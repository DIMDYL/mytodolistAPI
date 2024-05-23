package org.example.api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {
    private String token;
    private Long id;
}
