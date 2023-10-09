package com.pg.backend.mapper;

import com.pg.backend.annotation.AutoFill;
import com.pg.backend.constant.OperationType;
import com.pg.backend.entity.Post;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DemoMapper {
    @AutoFill(OperationType.UPDATE)
    @Select("select *  from db where id = #{id}")
    public Post query(int id);
    @Delete("delete from db where id = #{id}")
    void delete(int id);
    @AutoFill(OperationType.INSERT)
    @Insert("insert into db(info,create_time, update_time, create_user, update_user) value"+
   " (#{info},#{createTime}, #{updateTime}, #{createUser}, #{updateUser}) ")
    void save(Post post);
    @AutoFill(OperationType.UPDATE)
    @Update("update db set info = #{info} where id  = #{id}")
    void update(Post post);
}
