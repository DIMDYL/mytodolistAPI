package org.example.api.pojo.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Task implements Serializable {

    /**
     * 任务id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 代办内容
     */
    private String content;
    /**
     * 是否是限时任务
     */
    private int isLimitedTime;
    /**
     * 到期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime limitedTime;
    /**
     * 任务状态  1:完成 0:未完成
     */
    private int status;
    /**
     * 创建时间 00000-00-00 00:00:00
     */
    private LocalDateTime createTime;

}
