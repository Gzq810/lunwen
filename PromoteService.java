package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Promote;
import com.example.mapper.PromoteMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class PromoteService {

    @Resource
    private PromoteMapper promoteMapper;

    public void add(Promote promote) {
        promote.setTime(DateUtil.today());
        promote.setViews(0);
        promoteMapper.insert(promote);
    }

    public void updateById(Promote promote) {
        promoteMapper.updateById(promote);
    }

    public void deleteById(Integer id) {
        promoteMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            promoteMapper.deleteById(id);
        }
    }

    public Promote selectById(Integer id) {
        promoteMapper.updateViews(id);
        return promoteMapper.selectById(id);
    }

    public List<Promote> selectAll(Promote promote) {
        return promoteMapper.selectAll(promote);
    }

    public PageInfo<Promote> selectPage(Promote promote, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Promote> list = promoteMapper.selectAll(promote);
        return PageInfo.of(list);
    }

}
