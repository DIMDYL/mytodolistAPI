package org.example.api.controller.task;


import org.example.api.content.task.TaskMsg;
import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskQueryDTO;
import org.example.api.pojo.entity.Task;
import org.example.api.result.Result;
import org.example.api.service.TaskService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    @GetMapping("/query")
    public Result query(@Validated TaskQueryDTO dto){
        List<Task> taskScrollQuery = taskService.query(dto);
        return Result.success(taskScrollQuery);
    }


    /**
     * 修改任务状态
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/modifyStatus/{id}/{status}")
    public Result modifyStatus(@PathVariable @NotNull Long id,
                               @PathVariable @NotNull @Range(min = 0,max = 1) Integer status){
      taskService.modifyStatus(id,status);
      return Result.success(TaskMsg.MODIFY_TASK_STATUS_SUCCESS);
    }

    @PostMapping("/addTask")
    public Result addTask(@RequestBody @Validated TaskAddDTO dto){
        taskService.addTask(dto);
        return Result.success(TaskMsg.ADD_TASK_SUCCESS);
    }
}
