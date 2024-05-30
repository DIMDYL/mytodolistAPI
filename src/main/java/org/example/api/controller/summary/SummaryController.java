package org.example.api.controller.summary;

import org.example.api.content.summary.SummaryMsg;
import org.example.api.pojo.dto.SummaryAddDTO;
import org.example.api.pojo.dto.SummaryEditDTO;
import org.example.api.pojo.dto.SummaryScrollQueryDTO;
import org.example.api.pojo.entity.Summary;
import org.example.api.result.PageResult;
import org.example.api.result.Result;
import org.example.api.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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
           return Result.success(SummaryMsg.EDIT_SUMMARY_SUCCESS);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @NotNull @Min(0) Long id){
        service.delete(id);
        return Result.success(SummaryMsg.DELETE_SUMMARY_SUCCESS);
    }

    /**
     * 添加个人总结
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public Result<Summary> add(@RequestBody @Validated SummaryAddDTO dto){
        Summary object = service.add(dto);
        return Result.success(SummaryMsg.ADD_SUMMARY_SUCCESS,object);
    }

    /**
     * 滚动查询
     * @param dto
     * @return
     */
    @GetMapping("/scrollQuery")
    public Result scrollQuery(@Validated SummaryScrollQueryDTO dto){
        List<Summary> summaries = service.scrollQuery(dto);
        return Result.success(summaries);
    }

    @GetMapping("/getTotalNumber/{id}")
    public Result getTotalNumber(@PathVariable @NotNull Long id){
        Integer total = service.getTotalNumber(id);
        return Result.success(total);
    }
}
