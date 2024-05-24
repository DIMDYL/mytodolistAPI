package org.example.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.pojo.entity.Summary;

@Mapper
public interface SummaryMapper {
    void updateInfo(Summary dto);

    void delete(Long id);

    void add(Summary dto);
}
