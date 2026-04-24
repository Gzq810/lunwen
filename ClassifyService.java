package com.example.service;

import com.example.entity.Classify;
import com.example.mapper.ClassifyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class ClassifyService {

    @Resource
    private ClassifyMapper classifyMapper;

    public void add(Classify classify) {
        classifyMapper.insert(classify);
    }

    public void updateById(Classify classify) {
        classifyMapper.updateById(classify);
    }

    public void deleteById(Integer id) {
        classifyMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            classifyMapper.deleteById(id);
        }
    }

    public Classify selectById(Integer id) {
        return classifyMapper.selectById(id);
    }

    public List<Classify> selectAll(Classify classify) {
        return classifyMapper.selectAll(classify);
    }

    public PageInfo<Classify> selectPage(Classify classify, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Classify> list = classifyMapper.selectAll(classify);
        return PageInfo.of(list);
    }

}
