package org.example.api.controller.summary;

import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.result.Result;
import org.example.api.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/summary")
@Validated
public class SummaryController {

    @Autowired
    private SummaryService service;

    /**
     * 编辑用户信息
     * @param dto
     * @return
     */
    @PutMapping("/edit")
    public Result edit(@RequestBody @Validated SummaryEditDTO dto){
           service.edit(dto);
           return Result.success();
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @NotNull @Min(1) Long id){
        service.delete(id);
        return Result.success();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Validated SummaryAddDTO dto){
        service.add(dto);
        return Result.success();
    }
}
