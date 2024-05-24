package org.example.api.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SummaryEditDTO {

    /**
     * 总结的id号
     */
    @NotNull
    @Min(1)
    private Long id;
    /**
     * 代办内容
     */
    @NotBlank
    private String content;
}
