package org.example.api.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.pojo.dto.SummaryScrollQueryDTO;
import org.example.api.pojo.entity.Summary;

import java.util.List;

@Mapper
public interface SummaryMapper {
    void updateInfo(Summary dto);

    void delete(Long id);

    void add(Summary dto);

    List<Summary> scrollQuery(@Param("index") Integer index, @Param("number") Integer number, @Param("userId") Long userId);

    Integer queryTotalNumber(Long id);
}
