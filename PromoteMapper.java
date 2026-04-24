package com.example.mapper;

import com.example.entity.Promote;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PromoteMapper {

    int insert(Promote promote);

    void updateById(Promote promote);

    void deleteById(Integer id);

    @Select("select * from `promote` where id = #{id}")
    Promote selectById(Integer id);

    List<Promote> selectAll(Promote promote);

    @Update("update promote set views = views + 1 where id = #{id}")
    void updateViews(Integer id);

}
