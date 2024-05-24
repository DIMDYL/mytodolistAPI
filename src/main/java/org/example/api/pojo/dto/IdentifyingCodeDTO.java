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
    @NotNull
    @Range(min = 1,max = 2)
    private Integer type;
}
