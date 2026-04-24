package com.example.service;

import com.example.entity.Heritage;
import com.example.mapper.HeritageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class HeritageService {

    @Resource
    private HeritageMapper heritageMapper;

    public void add(Heritage heritage) {
        heritage.setViews(0);
        heritageMapper.insert(heritage);
    }

    public void updateById(Heritage heritage) {
        heritageMapper.updateById(heritage);
    }

    public void deleteById(Integer id) {
        heritageMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            heritageMapper.deleteById(id);
        }
    }

    public Heritage selectById(Integer id) {
        heritageMapper.updateViews(id);
        return heritageMapper.selectById(id);
    }

    public List<Heritage> selectAll(Heritage heritage) {
        return heritageMapper.selectAll(heritage);
    }

    public PageInfo<Heritage> selectPage(Heritage heritage, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Heritage> list = heritageMapper.selectAll(heritage);
        return PageInfo.of(list);
    }

}
