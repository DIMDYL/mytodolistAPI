package org.example.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.api.pojo.dto.TaskQueryDTO;
import org.example.api.pojo.entity.Task;

import java.util.List;

@Mapper
public interface TaskMapper {

    public List<Task> query(TaskQueryDTO dto);

    public void updateTask(Task task);

    public void addTask(Task task);

    void deleteTaskById(Long id);
}
