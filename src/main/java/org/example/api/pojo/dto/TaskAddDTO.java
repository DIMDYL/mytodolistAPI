package org.example.api.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TaskAddDTO {
    /**
     * 用户id {不能为空}
     */
    @NotNull
    @Min(1)
    private Long userId;

    /**
     * 代办内容 {不能为空}
     */
    @NotBlank
    private String content;

    /**
     * 是否为限时任务  0:不是,1:是 {不能为空，参数范围在0~1}
     */
    @NotNull
    @Range(min = 0,max = 1)
    private Integer isLimitedTime;


    /**
     * 限时时间 {不能为空}
     */
    private LocalTime limitedTime;
}
