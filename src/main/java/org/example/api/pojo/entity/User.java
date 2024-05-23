package org.example.api.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    private Long id;
    private String accountName;
    private String userName;
    private String password;
    private String image;
    private String email;
    private LocalDateTime createTime;
    private Integer status;
}
