package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Collect;
import com.example.mapper.CollectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class CollectService {

    @Resource
    private CollectMapper collectMapper;

    public void add(Collect collect) {
        Collect dbCollect = collectMapper.selectByActivityIdAndUserId(collect.getActivityId(), collect.getUserId());
        if (dbCollect != null) {
            // 收藏过活动  就删除收藏数据  取消收藏
            collectMapper.deleteById(dbCollect.getId());
        } else {
            // 收藏活动
            collect.setTime(DateUtil.now());
            collectMapper.insert(collect);
        }
    }

    public void updateById(Collect collect) {
        collectMapper.updateById(collect);
    }

    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            collectMapper.deleteById(id);
        }
    }

    public Collect selectById(Integer id) {
        return collectMapper.selectById(id);
    }

    public List<Collect> selectAll(Collect collect) {
        return collectMapper.selectAll(collect);
    }

    public PageInfo<Collect> selectPage(Collect collect, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Collect> list = collectMapper.selectAll(collect);
        return PageInfo.of(list);
    }

}
