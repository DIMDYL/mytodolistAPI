package org.example.api.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String accountName;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 图片地址
     */
    private String image;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    private LocalDate createTime;
    /**
     * 用户状态
     */
    private Integer status;
}
