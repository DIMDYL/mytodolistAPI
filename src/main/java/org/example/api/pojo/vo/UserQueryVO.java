package org.example.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryVO implements Serializable {

    private Long id;
    private String userName;
    private String image;
    private String email;
    private String createTime;
}
