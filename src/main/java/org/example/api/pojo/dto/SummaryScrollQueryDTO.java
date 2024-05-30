package org.example.api.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SummaryScrollQueryDTO {

    @NotNull
    @Min(0)
    private Integer start;

    @NotNull
    @Min(1)
    private Integer number;

    @NotNull
    @Min(1)
    private Long userId;

}
