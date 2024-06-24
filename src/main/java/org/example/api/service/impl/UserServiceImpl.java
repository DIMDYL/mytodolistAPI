package org.example.api.service.impl;

import cn.hutool.db.dialect.DialectFactory;
import org.bouncycastle.crypto.Digest;
import org.example.api.content.common.IdentifyingCodeMsg;
import org.example.api.content.common.FileMsg;
import org.example.api.content.common.RedisMsg;
import org.example.api.content.user.UserMsg;
import org.example.api.content.user.UserStatusMsg;
import org.example.api.context.BaseContext;
import org.example.api.exception.*;
import org.example.api.mapper.UserMapper;
import org.example.api.pojo.dto.IdentifyingCodeDTO;
import org.example.api.pojo.dto.UserLoginDTO;
import org.example.api.pojo.dto.UserSignupDTO;
import org.example.api.pojo.dto.VerifyIdentityDTO;
import org.example.api.pojo.entity.User;
import org.example.api.pojo.vo.UserQueryVO;
import org.example.api.properties.UploadProperties;
import org.example.api.service.UserService;
import org.example.api.utils.SendEmailUtils;
import org.example.api.utils.UploadUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UploadProperties uploadProperties;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SendEmailUtils emailUtils;

    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private RedisTemplate redisTemplate;



    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {

        String accountName = userLoginDTO.getAccountName();
        String password = userLoginDTO.getPassword();

//       根据用户名和密码查询 (ed:密码md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = new User();
        user.setPassword(password);
        user.setAccountName(accountName);

        User result = userMapper.queryUser(user);
        if(result == null){
             throw new LoginFailedException(UserMsg.ACCOUNTNAME_OR_PASSWORDW_WRONG);
        }
//      检测用户是否合法
        Integer status = result.getStatus();

        if(status == UserStatusMsg.FREEZED){
            throw new LoginFailedException(UserMsg.FREEZED_USER);
        }

        return result;
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @Override
    @RequestMapping
    public UserQueryVO queryUserById(Long id) {
//  ①查询用户
        User user = new User();
        user.setId(id);
        User queryUser = userMapper.queryUser(user);

        if(queryUser == null){
            throw new QueryFailedException(UserMsg.NO_USER);
        }
//  ②封装响应的用户数据
        UserQueryVO userQueryVO = new UserQueryVO();
        BeanUtils.copyProperties(queryUser,userQueryVO);
        return userQueryVO;
    }

    /**
     * 注册
     * @param dto
     * @return
     */
    @Override
    public User signup(UserSignupDTO dto) {
//  ① 检测验证码是否正确
        String key = RedisMsg.IDENTIFYING_PREFIX[IdentifyingCodeMsg.SIGNUP]+dto.getEmail();
        if(!this.checkingCodeIsCorrect(key,dto.getIdentifyingCode())){
            throw new SignupFailedException(IdentifyingCodeMsg.CHECKING_CODE_INCORRECT);
        }
//  ②判断邮箱是否已被绑定
        User user_ = new User();
        user_.setEmail(dto.getEmail());
        if(userMapper.queryUser(user_) != null){
            throw new BaseException(UserMsg.EXISTED_EMAIL);
        }
//  ③封装用户信息
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        //密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        //状态
        user.setStatus(UserStatusMsg.INVOKED);
        //加入时间
        user.setCreateTime(LocalDate.now());
        //默认头像
        user.setImage("http://localhost:8080/mytodolist/img/"+"3ea6beec64369c2642b92c6726f1epng.png");
//  ③添加信息
        userMapper.insertOne(user);

        return user;
    }

    /**
     * 修改用户信息
     * @param id
     * @param image
     * @param userName
     * @param email
     * @param identifyingCode
     */
    @Override
    public void modifyUserInfo(Long id, MultipartFile image, String userName, String email, String identifyingCode) {
//  ①数据准备
        String imagePath = null;
//  ②验证
//     验证码是否正确  ? 为什么我不使用constrain? ①不能对象封装 ②需要同时有email + checkingCode
        String key = RedisMsg.IDENTIFYING_PREFIX[IdentifyingCodeMsg.EDIT_INFO]+email;
        if(!this.checkingCodeIsCorrect(key,identifyingCode)) {
            throw new SignupFailedException(IdentifyingCodeMsg.CHECKING_CODE_INCORRECT);
        }

//  ③存储图片文件
        if(image != null) {
            try {
                imagePath = "http://localhost:8080/mytodolist/img/"+uploadUtils.upload(image);
            } catch (Exception e) {
                throw new ModifyUserInfoFailedException(FileMsg.UPLOAD_IMAGE_FAILED);
            }
        }



//  ④更新数据
        User user = new User();
        user.setId(id);
        user.setImage(imagePath);
        user.setUserName(userName);
        user.setEmail(email);
        userMapper.updateUserInfo(user);
    }


    /**
     * 发送验证码
     * @param identifyingCodeDTO
     */
    @Override
    public void sendCheckingCode(IdentifyingCodeDTO identifyingCodeDTO) {
//        TODO 检测传参是否合法
/**
 *  ①信息准备
 *           1. 验证码类型
 *           2. 目标邮箱
 *           3. 标题
 *           4. 内容
 *           5. 验证码 (ed: 6位数)
 *
 */
        Integer type = identifyingCodeDTO.getType();
        String targetEmail = identifyingCodeDTO.getEmail();
        String title = "";
        String content ="";
        int checkingCode = (int)(Math.floor(Math.random()*1000000));
        if(type == IdentifyingCodeMsg.SIGNUP){
            title = "用户注册验证码";
        }else if(type == IdentifyingCodeMsg.EDIT_INFO){
            title = "编辑用户验证码";
        }else if(type == IdentifyingCodeMsg.RESTORE_USER){
            title = "重置用户验证码";
        }
        content = "验证码为: "+checkingCode;

/**
 *   ②发送验证码
 */
        emailUtils.sendSimpleEmail(targetEmail,title,content);

/**
 *  ③存储验证码  (ed: 过期时间 {一分钟})
 */
        String key = RedisMsg.IDENTIFYING_PREFIX[type]+targetEmail;
        Long ttl = RedisMsg.IDENTIFYING_TTL[type];
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,checkingCode+"", ttl,TimeUnit.SECONDS);
    }

    @Override
    public void modifyPassword(String password) {
//        密码加密
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
//        更新密码
        User user = new User();
        user.setPassword(s);
        user.setId(BaseContext.getUserId());

        userMapper.updateUserInfo(user);
    }

    /**
     * 获取用户
     * @param userName
     * @return
     */
    @Override
    public String isExistedForUserWithUserName(String userName) {
//        判断是否存在
        User user = new User();
        user.setUserName(userName);
        User userInfo = userMapper.queryUser(user);

        if(userInfo == null){
            throw new BaseException(UserMsg.NO_USER);
        }
//        返回邮箱信息
        return userInfo.getEmail();
    }

    /**
     * 身份校验
     * @param verifyIdentityDTO
     * @return
     */
    @Override
    public User verifyIdentity(VerifyIdentityDTO verifyIdentityDTO) {
//        校验验证码是否current
        String key = RedisMsg.IDENTIFYING_PREFIX[IdentifyingCodeMsg.RESTORE_USER] +
                     verifyIdentityDTO.getEmail();
        if(!checkingCodeIsCorrect(key,verifyIdentityDTO.getIdentifyingCode())){
            throw new BaseException(IdentifyingCodeMsg.CHECKING_CODE_INCORRECT);
        }

//        获取用户信息
        User user = new User();
        user.setEmail(verifyIdentityDTO.getEmail());
        User userInfo = userMapper.queryUser(user);

//        返回用户信息
        return userInfo;
    }

    /**
     * 内部方法 {检验验证码是否正确}
     * @param key
     * @param target
     * @return
     */
    private boolean checkingCodeIsCorrect(String key,String target){
        Object o = redisTemplate.opsForValue().get(key);
        if(o == null){
            return false;
        }
        String realCheckingCode = o.toString();
        return realCheckingCode.equals(target);
    }
}
