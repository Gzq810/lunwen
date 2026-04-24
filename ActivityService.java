package com.example.service;

import com.example.entity.Account;
import com.example.entity.Activity;
import com.example.entity.Collect;
import com.example.mapper.ActivityMapper;
import com.example.mapper.CollectMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class ActivityService {

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    CollectMapper collectMapper;

    public void add(Activity activity) {
        activity.setNum(0);
        activity.setStatus("报名中");
        activityMapper.insert(activity);
    }

    public void updateById(Activity activity) {
        activityMapper.updateById(activity);
    }

    public void deleteById(Integer id) {
        activityMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            activityMapper.deleteById(id);
        }
    }

    public Activity selectById(Integer id) {
        Account currentUser = TokenUtils.getCurrentUser();
        Activity activity = activityMapper.selectById(id);
        Collect collect = collectMapper.selectByActivityIdAndUserId(id, currentUser.getId());
        activity.setUserCollect(collect != null);
        return activity;
    }

    public List<Activity> selectAll(Activity activity) {
        return activityMapper.selectAll(activity);
    }

    public PageInfo<Activity> selectPage(Activity activity, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectAll(activity);
        return PageInfo.of(list);
    }

}
