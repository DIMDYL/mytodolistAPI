package org.example.api.content.common;

public class IdentifyingCodeMsg {
//    注册用户的验证码
    public static final Integer SIGNUP = 1;
//    编辑用户信息的验证码
    public static final Integer EDIT_INFO = 2;
// 用户验证码
    public static final Integer RESTORE_USER = 3;


//    验证码消息
    public static final String CHECKING_CODE_INCORRECT = "验证码不正确";

    public static final String SEND_CHECKING_CODE_FAILED ="发送验证码失败";
    public static final String SEND_CHECKING_CODE_SUCCESS ="发送验证码成功";

}
