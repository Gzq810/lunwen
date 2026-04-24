package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Activity;
import com.example.entity.Enroll;
import com.example.exception.CustomException;
import com.example.mapper.ActivityMapper;
import com.example.mapper.EnrollMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class EnrollService {

    @Resource
    private EnrollMapper enrollMapper;
    @Resource
    ActivityMapper activityMapper;

    public void add(Enroll enroll) {
        // 查询是否已经报名
        Enroll dbEnroll = enrollMapper.selectByUserIdAndActivityId(enroll.getUserId(), enroll.getActivityId());
        if (dbEnroll != null) {
            throw new CustomException("500", "报名失败！您已经报名过该活动");
        }
        enroll.setTime(DateUtil.now());
        enroll.setStatus("待审核");
        enrollMapper.insert(enroll);
    }

    @Transactional
    public void updateById(Enroll enroll) {
        if (enroll.getStatus().equals("审核通过")) {
            Integer activityId = enroll.getActivityId();
            Activity activity = activityMapper.selectById(activityId);
            activity.setNum(activity.getNum() + 1);
            activityMapper.updateById(activity);
        }
        enrollMapper.updateById(enroll);
    }

    public void deleteById(Integer id) {
        enrollMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            enrollMapper.deleteById(id);
        }
    }

    public Enroll selectById(Integer id) {
        return enrollMapper.selectById(id);
    }

    public List<Enroll> selectAll(Enroll enroll) {
        return enrollMapper.selectAll(enroll);
    }

    public PageInfo<Enroll> selectPage(Enroll enroll, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Enroll> list = enrollMapper.selectAll(enroll);
        return PageInfo.of(list);
    }

}
