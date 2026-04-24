package com.example.mapper;

import com.example.entity.Heritage;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface HeritageMapper {

    int insert(Heritage heritage);

    void updateById(Heritage heritage);

    void deleteById(Integer id);

    @Select("select * from `heritage` where id = #{id}")
    Heritage selectById(Integer id);

    List<Heritage> selectAll(Heritage heritage);

    @Update("update `heritage` set views = views + 1 where id = #{id}")
    void updateViews(Integer id);

}
