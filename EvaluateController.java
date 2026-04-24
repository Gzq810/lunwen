package com.example.controller;

import com.example.common.Result;
import com.example.entity.Evaluate;
import com.example.service.EvaluateService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/evaluate")
public class EvaluateController {

    @Resource
    private EvaluateService evaluateService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Evaluate evaluate) {
        evaluateService.add(evaluate);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Evaluate evaluate) {
        evaluateService.updateById(evaluate);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        evaluateService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        evaluateService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Evaluate evaluate = evaluateService.selectById(id);
        return Result.success(evaluate);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Evaluate evaluate) {
        List<Evaluate> list = evaluateService.selectAll(evaluate);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Evaluate evaluate,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Evaluate> pageInfo = evaluateService.selectPage(evaluate, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
