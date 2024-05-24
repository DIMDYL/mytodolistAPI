package org.example.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.formula.functions.T;
import org.example.api.mapper.TaskMapper;
import org.example.api.pojo.dto.TaskAddDTO;
import org.example.api.pojo.dto.TaskPageQueryDTO;
import org.example.api.pojo.entity.Task;
import org.example.api.result.PageResult;
import org.example.api.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.time.LocalDateTime;

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
    public PageResult<Task> pageQuery(TaskPageQueryDTO dto) {
//        ①调用分页查询插件
        PageHelper.startPage(dto.getPage(),dto.getPageSize());

//        ②查询数据
        Page page = mapper.pageQuery(dto);
//        ③封装数据
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRecords(page.getResult());

        return result;
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
        Task task = new Task();
        BeanUtils.copyProperties(dto,task);

//        ①封装数据
        task.setCreateTime(LocalDateTime.now());
//        ②添加数据
        mapper.addTask(task);
    }
}
