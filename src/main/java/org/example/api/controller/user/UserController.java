package org.example.api.controller.user;

import org.apache.logging.log4j.message.Message;
import org.example.api.constrain.annotation.Img;
import org.example.api.content.user.UserMsg;
import org.example.api.pojo.dto.*;
import org.example.api.pojo.entity.User;
import org.example.api.pojo.vo.UserLoginVO;
import org.example.api.pojo.vo.UserQueryVO;
import org.example.api.properties.JwtProperties;
import org.example.api.result.Result;
import org.example.api.service.UserService;
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
        return Result.success(vo);
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
        return Result.success(vo);
    }

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable @NotNull @Min(0) Long id){
        UserQueryVO userQueryVO = userService.queryUserById(id);
        return Result.success(userQueryVO);
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
        return Result.success();
    }
    /**
     * 发送验证码
     * @param identifyingCodeDTO
     * @return
     */
    @PostMapping("/sendCheckingCode")
    public Result sendCheckingCode(@RequestBody @Validated IdentifyingCodeDTO identifyingCodeDTO){
          userService.sendCheckingCode(identifyingCodeDTO);

          return Result.success();
    }

}
