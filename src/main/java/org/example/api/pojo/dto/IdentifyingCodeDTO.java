package org.example.api.pojo.dto;

import lombok.Data;
import org.example.api.content.user.UserMsg;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class IdentifyingCodeDTO {

    @NotBlank(message = UserMsg.NO_EMAIL)
    @Email
    private String email;
    @NotBlank(message = "验证类型为空")
    @Max(value = 2,message = "验证类型不正确")
    @Min(value = 1,message = "验证类型不正确")
    private Integer type;
}
