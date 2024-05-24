package org.example.api.service.impl;

import org.example.api.mapper.SummaryMapper;
import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.pojo.entity.Summary;
import org.example.api.service.SummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private SummaryMapper mapper;

    /**
     * 编辑总结信息
     * @param dto
     */
    @Override
    public void edit(SummaryEditDTO dto) {
//        ①封装信息
        Summary summary = new Summary();
        BeanUtils.copyProperties(dto,summary);
        summary.setUpdateTime(LocalDateTime.now());
//        ②修改数据
        mapper.updateInfo(summary);
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }

    @Override
    public void add(SummaryAddDTO dto) {
//        ①封装数据
        Summary summary = new Summary();
        BeanUtils.copyProperties(dto,summary);

        summary.setCreateTime(LocalDateTime.now());
        summary.setUpdateTime(LocalDateTime.now());
//        ②添加信息
        mapper.add(summary);
    }

}
