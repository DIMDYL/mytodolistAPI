package org.example.api.service;

import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.pojo.dto.SummaryScrollQueryDTO;
import org.example.api.pojo.entity.Summary;
import org.example.api.result.PageResult;

import java.util.List;

public interface SummaryService {

    public void edit(SummaryEditDTO dto);

    void delete(Long id);

    Summary add(SummaryAddDTO dto);

    List<Summary> scrollQuery(SummaryScrollQueryDTO dto);

    Integer getTotalNumber(Long id);
}
