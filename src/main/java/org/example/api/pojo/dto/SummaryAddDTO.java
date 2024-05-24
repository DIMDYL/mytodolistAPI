package org.example.api.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SummaryAddDTO {

    @NotNull
    @Min(1)
    private Long userId;
    @NotBlank
    private String content;
}
