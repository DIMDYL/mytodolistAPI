package org.example.api.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.example.api.pojo.dto.TaskPageQueryDTO;
import org.example.api.pojo.entity.Task;

@Mapper
public interface TaskMapper {

    public Page<Task> pageQuery(TaskPageQueryDTO dto);

    public void updateTask(Task task);

    public void addTask(Task task);
}
