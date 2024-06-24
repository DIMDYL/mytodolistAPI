package org.example.api.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class VerifyIdentityDTO {

    /**
     * 待校验的邮箱
     */
    @Email
    @NotBlank
    private String email;

    /**
     * 验证码
     */
    @NotBlank
    private String identifyingCode;
}
