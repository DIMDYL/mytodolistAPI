package org.example.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserQueryVO implements Serializable {

    private Long id;
    private String userName;
    private String image;
    private String email;
    private LocalDate createTime;
}
