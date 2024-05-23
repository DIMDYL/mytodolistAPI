package org.example.api.pojo.dto;

import lombok.Data;
import org.example.api.content.user.UserMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable{

    @NotBlank(message = UserMsg.NO_ACCOUNT_NAME)
    private String accountName;
    @NotBlank(message = UserMsg.NO_PASSWORD)
    private String password;
}
