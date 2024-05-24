package org.example.api.pojo.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Summary {

    /**
     * 总结的id号
     */
    @NotNull
    @Min(1)
    private Long id;
    /**
     * 用户的id号
     */
    @NotNull
    @Min(1)
    private Long userId;
    /**
     * 个人总结内容
     */
    @NotBlank
    private String content;
    /**
     * 个人总结创建时间
     */
    @NotNull
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @NotNull
    private LocalDateTime updateTime;
}
