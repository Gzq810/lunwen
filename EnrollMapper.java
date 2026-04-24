package com.example.mapper;

import com.example.entity.Enroll;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EnrollMapper {

    int insert(Enroll enroll);

    void updateById(Enroll enroll);

    void deleteById(Integer id);

    @Select("select * from `enroll` where id = #{id}")
    Enroll selectById(Integer id);

    List<Enroll> selectAll(Enroll enroll);

    @Select("select * from `enroll` where user_id = #{userId} and activity_id = #{activityId} " +
            "and (status = '待审核' or status = '审核通过')")
    Enroll selectByUserIdAndActivityId(@Param("userId") Integer userId, @Param("activityId") Integer activityId);

}
