package com.example.mapper;

import com.example.entity.Classify;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClassifyMapper {

    int insert(Classify classify);

    void updateById(Classify classify);

    void deleteById(Integer id);

    @Select("select * from `classify` where id = #{id}")
    Classify selectById(Integer id);

    List<Classify> selectAll(Classify classify);

}
