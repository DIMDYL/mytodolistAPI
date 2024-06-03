package org.example.api.service;

import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskEditDTO;
import org.example.api.pojo.dto.TaskQueryDTO;
import org.example.api.pojo.entity.Task;

import java.util.List;

public interface TaskService {


   public List<Task> query(TaskQueryDTO dto);

   void modifyStatus(Long id, Integer status);

   void addTask(TaskAddDTO dto);

    void deleteTaskById(Long id);

    void modifyContent(TaskEditDTO taskEditDTO);
}
