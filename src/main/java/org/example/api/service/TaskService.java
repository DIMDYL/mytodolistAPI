package org.example.api.service;

import com.github.pagehelper.Page;
import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskPageQueryDTO;
import org.example.api.pojo.entity.Task;
import org.example.api.result.PageResult;

public interface TaskService {


   public PageResult<Task> pageQuery(TaskPageQueryDTO dto);

   void modifyStatus(Long id, Integer status);

   void addTask(TaskAddDTO dto);
}
