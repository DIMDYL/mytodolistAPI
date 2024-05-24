package org.example.api.service;

import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;

public interface SummaryService {

    public void edit(SummaryEditDTO dto);

    void delete(Long id);

    void add(SummaryAddDTO dto);
}
