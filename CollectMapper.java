package com.example.mapper;

import com.example.entity.Collect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper {

    int insert(Collect collect);

    void updateById(Collect collect);

    void deleteById(Integer id);

    @Select("select * from `collect` where id = #{id}")
    Collect selectById(Integer id);

    List<Collect> selectAll(Collect collect);

    @Select("select * from `collect` where activity_id = #{activityId} and user_id = #{userId}")
    Collect selectByActivityIdAndUserId(@Param("activityId") Integer activityId, @Param("userId") Integer userId);

}
