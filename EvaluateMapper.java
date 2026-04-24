package com.example.mapper;

import com.example.entity.Evaluate;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EvaluateMapper {

    int insert(Evaluate evaluate);

    void updateById(Evaluate evaluate);

    void deleteById(Integer id);

    @Select("select * from `evaluate` where id = #{id}")
    Evaluate selectById(Integer id);

    List<Evaluate> selectAll(Evaluate evaluate);

}
