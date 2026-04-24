package com.example.controller;

import com.example.common.Result;
import com.example.entity.Promote;
import com.example.service.PromoteService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/promote")
public class PromoteController {

    @Resource
    private PromoteService promoteService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Promote promote) {
        promoteService.add(promote);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Promote promote) {
        promoteService.updateById(promote);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        promoteService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        promoteService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Promote promote = promoteService.selectById(id);
        return Result.success(promote);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Promote promote) {
        List<Promote> list = promoteService.selectAll(promote);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Promote promote,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Promote> pageInfo = promoteService.selectPage(promote, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
