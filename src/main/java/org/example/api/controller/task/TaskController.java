package org.example.api.controller.task;


import org.apache.ibatis.annotations.Delete;
import org.example.api.content.task.TaskMsg;
import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskEditDTO;
import org.example.api.pojo.dto.TaskQueryDTO;
import org.example.api.pojo.entity.Task;
import org.example.api.result.Result;
import org.example.api.service.TaskService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
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
        return Result.success(null,taskScrollQuery);
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
      return Result.success(TaskMsg.MODIFY_TASK_STATUS_SUCCESS,null);
    }

    /**
     * 添加任务
     * @param dto
     * @return
     */
    @PostMapping("/addTask")
    public Result addTask(@RequestBody @Validated TaskAddDTO dto){
        taskService.addTask(dto);
        return Result.success(TaskMsg.ADD_TASK_SUCCESS,null);
    }


    /**
     * 根据task的Id删除任务
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteTask(@PathVariable @Min(1) Long id){
        taskService.deleteTaskById(id);
        return Result.success(TaskMsg.DELETE_SUCCESS,null);
    }

    /**
     * 编辑task内容
     * @param taskEditDTO
     * @return
     */
    @PutMapping("/edit")
    public Result edit(@RequestBody @Validated TaskEditDTO taskEditDTO){
        taskService.modifyContent(taskEditDTO);
        return Result.success(TaskMsg.MODIFY_TASK_CONTENT_SUCCESS,null);
    }



}
