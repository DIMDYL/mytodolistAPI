package org.example.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.api.mapper.SummaryMapper;
import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.pojo.dto.SummaryScrollQueryDTO;
import org.example.api.pojo.entity.Summary;
import org.example.api.result.PageResult;
import org.example.api.service.SummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 添加个人总结
     * @param dto
     */
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

    /**
     * 滚动查询数据
     * @param dto
     * @return
     */
    @Override
    public List<Summary> scrollQuery(SummaryScrollQueryDTO dto) {
//       ①数据封装
        Integer index = dto.getNumber()*(dto.getScrollTimes()-1);
        Integer number = dto.getNumber();
//       ②查询数据
         List<Summary> summaries = mapper.scrollQuery(index,number,dto.getUserId());
         return summaries;
    }

}
