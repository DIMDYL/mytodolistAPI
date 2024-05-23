package org.example.api.pojo.dto;

import lombok.Data;
import org.example.api.content.user.UserMsg;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserSignupDTO {

    @NotBlank(message = UserMsg.NO_ACCOUNT_NAME)
    private String accountName;
    @NotBlank(message = UserMsg.NO_USER_NAME)
    private String userName;
    @NotBlank(message = UserMsg.NO_PASSWORD)
    private String password;

    @NotBlank(message = UserMsg.NO_EMAIL)
    @Email
    private String email;
    @NotBlank(message = UserMsg.NO_IDENTIFYING_CODE)
    private String identifyingCode;
}
