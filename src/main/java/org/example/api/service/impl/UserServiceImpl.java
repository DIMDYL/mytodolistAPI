package org.example.api.service.impl;

import org.example.api.content.IdentifyingCodeMsg;
import org.example.api.content.FileMsg;
import org.example.api.content.user.UserMsg;
import org.example.api.content.user.UserStatusMsg;
import org.example.api.exception.LoginFailedException;
import org.example.api.exception.ModifyUserInfoFailedException;
import org.example.api.exception.QueryFailedException;
import org.example.api.exception.SignupFailedException;
import org.example.api.mapper.UserMapper;
import org.example.api.pojo.dto.IdentifyingCodeDTO;
import org.example.api.pojo.dto.UserLoginDTO;
import org.example.api.pojo.dto.UserSignupDTO;
import org.example.api.pojo.entity.User;
import org.example.api.pojo.vo.UserQueryVO;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {


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
        if(!this.checkingCodeIsCorrect(dto.getEmail(),dto.getIdentifyingCode())){
            throw new SignupFailedException(IdentifyingCodeMsg.CHECKING_CODE_INCORRECT);
        }

//  ②封装用户信息
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        //密码
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        //状态
        user.setStatus(UserStatusMsg.INVOKED);
        //加入时间
        user.setCreateTime(LocalDate.now());

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
        String imagePath = "";
//  ②验证
//     验证码是否正确  ? 为什么我不使用constrain? ①不能对象封装 ②需要同时有email + checkingCode
        if(!this.checkingCodeIsCorrect(email,identifyingCode)) {
            throw new SignupFailedException(IdentifyingCodeMsg.CHECKING_CODE_INCORRECT);
        }

//  ③存储图片文件
        try {
            imagePath = uploadUtils.upload(image);
        } catch (Exception e) {
            throw new ModifyUserInfoFailedException(FileMsg.UPLOAD_IMAGE_FAILED);
        }



//  ④更新数据
        User user = new User();
        user.setId(id);
        imagePath = "http://localhost:8080/mytodolist/img/"+imagePath;
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
        }
        content = "验证码为: "+checkingCode;

/**
 *   ②发送验证码
 */
        emailUtils.sendEmail(targetEmail,title,content);

/**
 *  ③存储验证码  (ed: 过期时间 {一分钟})
 */
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(targetEmail,checkingCode+"", Duration.ofSeconds(60));
    }

    /**
     * 内部方法 {检验验证码是否正确}
     * @param email
     * @param target
     * @return
     */
    private boolean checkingCodeIsCorrect(String email,String target){
        Object o = redisTemplate.opsForValue().get(email);
        if(o == null){
            return false;
        }
        String realCheckingCode = o.toString();
        return realCheckingCode.equals(target);
    }
}
