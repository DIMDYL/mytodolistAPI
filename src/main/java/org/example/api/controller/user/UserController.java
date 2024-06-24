package org.example.api.controller.user;

import org.example.api.constrain.annotation.Img;
import org.example.api.content.common.IdentifyingCodeMsg;
import org.example.api.content.user.UserMsg;
import org.example.api.pojo.dto.*;
import org.example.api.pojo.entity.User;
import org.example.api.pojo.vo.UserLoginVO;
import org.example.api.pojo.vo.UserQueryVO;
import org.example.api.properties.JwtProperties;
import org.example.api.result.Result;
import org.example.api.service.UserService;
import org.example.api.utils.HidingInfoUtil;
import org.example.api.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 校验用户合法性
     * @return
     */
    @GetMapping("/verifyUser")
    public Result verifyUser(){
        return Result.success();
    }

    /**
     * 校验身份信息
     * @param verifyIdentityDTO
     * @return
     */
    @PostMapping("/verifyIdentity")
    public Result verifyIdentity(@RequestBody VerifyIdentityDTO verifyIdentityDTO){
//        校验是否合法
      User user = userService.verifyIdentity(verifyIdentityDTO);
//        生成token
      Map map = new HashMap();
      map.put(jwtProperties.getUserId(),user.getId());
      String token = JwtUtils.CreateJWT(jwtProperties, map);
//        返回token
      return Result.success(null,token);
    }
    /**
     * 登录
     *
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Validated UserLoginDTO dto){
        User user = userService.login(dto);
//        创建token
        Map<String,Object> map = new HashMap<>();
        map.put(jwtProperties.getUserId(),user.getId());
        String token = JwtUtils.CreateJWT(jwtProperties, map);

//        响应
        UserLoginVO vo = new UserLoginVO();
        vo.setId(user.getId());
        vo.setToken(token);
        return Result.success(null,vo);
    }

    /**
     * 注册
     * @param dto
     * @return
     */
    @PostMapping("/signup")
    public Result signup(@RequestBody @Validated UserSignupDTO dto){
//        ①注册用户
        User user = userService.signup(dto);
//        ②创建token
        Map<String,Object> map = new HashMap<>();
        map.put(jwtProperties.getUserId(),user.getId());
        String token = JwtUtils.CreateJWT(jwtProperties, map);

//        ③响应
        UserLoginVO vo = new UserLoginVO();
        vo.setId(user.getId());
        vo.setToken(token);
        return Result.success(null,vo);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable @NotNull @Min(0) Long id){
        UserQueryVO userQueryVO = userService.queryUserById(id);
        return Result.success(null,userQueryVO);
    }
    /**
     * 编辑用户信息
     * @param
     * @return
     */
    @PutMapping("/edit")
    public Result modifyUserInfo(@NotNull(message = UserMsg.NO_USER_ID) Long id, @Img MultipartFile image,
                                 @NotBlank(message = UserMsg.NO_USER_NAME) String userName,
                                 @NotBlank(message = UserMsg.NO_EMAIL) @Email String email,
                                 @NotBlank(message = UserMsg.NO_IDENTIFYING_CODE) String identifyingCode){
        userService.modifyUserInfo(id, image, userName, email, identifyingCode);
        return Result.success(UserMsg.EDIT_USER_INFO_SUCCESS,null);
    }
    /**
     * 设置新密码
     */
    @PutMapping("/edit/{password}")
    public Result modifyPassword(@PathVariable @NotBlank String password){
        userService.modifyPassword(password);
        return Result.success("密码设置成功",null);
    }
    /**
     *  获取存在用户的邮箱(脱敏后的)
     * @param userName
     * @return
     */
    @GetMapping("/isExistedForUserWithUserName/{userName}")
    public Result isExistedForUserWithUserName(@PathVariable @NotBlank String userName){
//      获取邮箱
        String email = userService.isExistedForUserWithUserName(userName);

//      生成脱敏邮箱
        String sEmail = HidingInfoUtil.emailHide(email);

//      返回值
        return Result.success(null,sEmail);
    }

 /**
     * 发送验证码
     * @param identifyingCodeDTO
     * @return
     */
    @PostMapping("/sendIdentifyingCode")
    public Result sendCheckingCode(@RequestBody @Validated IdentifyingCodeDTO identifyingCodeDTO) {
        userService.sendCheckingCode(identifyingCodeDTO);
        return Result.success(IdentifyingCodeMsg.SEND_CHECKING_CODE_SUCCESS,null);
    }

}
