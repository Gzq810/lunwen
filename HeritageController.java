package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Heritage;
import com.example.service.HeritageService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/heritage")
public class HeritageController {

    @Resource
    private HeritageService heritageService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Heritage heritage) {
        heritage.setTime(DateUtil.now());
        heritageService.add(heritage);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Heritage heritage) {
        heritageService.updateById(heritage);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        heritageService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        heritageService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Heritage heritage = heritageService.selectById(id);
        return Result.success(heritage);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Heritage heritage) {
        List<Heritage> list = heritageService.selectAll(heritage);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Heritage heritage,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Heritage> pageInfo = heritageService.selectPage(heritage, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
