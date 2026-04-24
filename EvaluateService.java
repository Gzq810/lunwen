package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Evaluate;
import com.example.mapper.EvaluateMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class EvaluateService {

    @Resource
    private EvaluateMapper evaluateMapper;

    public void add(Evaluate evaluate) {
        evaluate.setTime(DateUtil.now());
        evaluateMapper.insert(evaluate);
    }

    public void updateById(Evaluate evaluate) {
        evaluateMapper.updateById(evaluate);
    }

    public void deleteById(Integer id) {
        evaluateMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            evaluateMapper.deleteById(id);
        }
    }

    public Evaluate selectById(Integer id) {
        return evaluateMapper.selectById(id);
    }

    public List<Evaluate> selectAll(Evaluate evaluate) {
        return evaluateMapper.selectAll(evaluate);
    }

    public PageInfo<Evaluate> selectPage(Evaluate evaluate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Evaluate> list = evaluateMapper.selectAll(evaluate);
        return PageInfo.of(list);
    }

}
