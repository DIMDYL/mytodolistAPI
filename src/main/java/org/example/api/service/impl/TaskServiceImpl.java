package org.example.api.service.impl;

import org.example.api.content.task.TaskMsg;
import org.example.api.exception.AddTaskFailedException;
import org.example.api.mapper.TaskMapper;
import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskEditDTO;
import org.example.api.pojo.dto.TaskQueryDTO;
import org.example.api.pojo.entity.Task;
import org.example.api.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper mapper;

    /**
     * 页面查询Task
     * @param dto
     * @return
     */
    @Override
    public List<Task> query(TaskQueryDTO dto) {
//        ②查询数据
        List<Task> data = mapper.query(dto);
        return data;
    }


    /**
     * 修改任务状态
     * @param id
     * @param status
     */
    @Override
    public void modifyStatus(Long id, Integer status) {
        Task task = new Task();
        task.setId(id);
        task.setStatus(status);

        mapper.updateTask(task);
    }

    /**
     * 添加代办事项
     * @param dto
     */
    @Override
    public void addTask(TaskAddDTO dto) {
//        ①检测数据
        if(dto.getIsLimitedTime() == TaskMsg.IS_LIMITED){
            if(dto.getLimitedTime() == null){
                throw new AddTaskFailedException(TaskMsg.NO_LIMITED_TIME);
            }
        }
        Task task = new Task();
        BeanUtils.copyProperties(dto,task);

//        ①封装数据
        task.setStatus(TaskMsg.UNFINISHED);
        task.setCreateTime(LocalDateTime.now());
//        ②添加数据
        mapper.addTask(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        mapper.deleteTaskById(id);
    }

    @Override
    public void modifyContent(TaskEditDTO taskEditDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskEditDTO,task);
        mapper.updateTask(task);
    }
}
