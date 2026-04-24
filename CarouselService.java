package com.example.service;

import com.example.entity.Carousel;
import com.example.mapper.CarouselMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    public void add(Carousel carousel) {
        carouselMapper.insert(carousel);
    }

    public void updateById(Carousel carousel) {
        carouselMapper.updateById(carousel);
    }

    public void deleteById(Integer id) {
        carouselMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            carouselMapper.deleteById(id);
        }
    }

    public Carousel selectById(Integer id) {
        return carouselMapper.selectById(id);
    }

    public List<Carousel> selectAll(Carousel carousel) {
        return carouselMapper.selectAll(carousel);
    }

    public PageInfo<Carousel> selectPage(Carousel carousel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Carousel> list = carouselMapper.selectAll(carousel);
        return PageInfo.of(list);
    }

}
