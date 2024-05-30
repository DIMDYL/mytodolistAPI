package org.example.api.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class TaskQueryDTO {

    /**
     * 起始位置 {不能为空，最小值是1}
     */
    private Integer start;
    /**
     * 更新条数 {不能为空，最小值是1}
     */
    private Integer number;

    /**
     * 用户的id  {不能为空}
     */
    @NotNull
    private Long userId;

    /**
     * 是否是限时任务  0:不是 1:是 {不能为空，参数范围0~1}
     */
    @NotNull
    @Range(min = 0,max = 1,message = "限时的状态必须在0~1之间")
    private Integer isLimitedTime;
    /**
     * 任务状态  0:未完成  1:完成 {不能为空，参数范围0~1}
     */
    @NotNull
    @Range(min = 0,max = 1,message = "限时的状态必须在0~1之间")
    private Integer status;
}
