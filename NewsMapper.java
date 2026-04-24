package com.example.mapper;

import com.example.entity.News;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NewsMapper {

    int insert(News news);

    void updateById(News news);

    void deleteById(Integer id);

    @Select("select * from `news` where id = #{id}")
    News selectById(Integer id);

    List<News> selectAll(News news);

    @Update("update `news` set views = views + 1 where id = #{id}")
    void updateViews(Integer id);

}
