package org.example.api.controller.task;


import com.github.pagehelper.Page;
import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskPageQueryDTO;
import org.example.api.pojo.entity.Task;
import org.example.api.result.PageResult;
import org.example.api.result.Result;
import org.example.api.service.TaskService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {


    @Autowired
    private TaskService taskService;

    /**
     * 页面查询代办事项
     * @param dto
     * @return
     */
    @GetMapping("/pageQuery")
    public Result pageQuery(@Validated TaskPageQueryDTO dto){
        PageResult<Task> taskPageResult = taskService.pageQuery(dto);
        return Result.success(taskPageResult);
    }


    /**
     * 修改任务状态
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/modifyStatus")
    public Result modifyStatus(@NotNull Long id,
                               @NotNull @Range(min = 0,max = 1) Integer status){
      taskService.modifyStatus(id,status);
      return Result.success();
    }

    @PostMapping("/addTask")
    public Result addTask(@RequestBody @Validated TaskAddDTO dto){
        taskService.addTask(dto);
        return Result.success();
    }
}
