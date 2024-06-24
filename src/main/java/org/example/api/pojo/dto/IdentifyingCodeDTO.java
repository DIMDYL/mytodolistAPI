package org.example.api.pojo.dto;

import lombok.Data;
import org.example.api.content.user.UserMsg;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class IdentifyingCodeDTO {

    @NotBlank(message = UserMsg.NO_EMAIL)
    @Email
    private String email;
    /**
     * 1: 注册 2:编辑用户 3:重置密码
     */
    @NotNull
    @Range(min = 1,max = 3)
    private Integer type;
}
