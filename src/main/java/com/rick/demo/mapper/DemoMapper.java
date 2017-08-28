package com.rick.demo.mapper;

import com.rick.demo.entity.Demo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DemoMapper {


    @Select("select * from demo where name =#{name}")
    public List<Demo> likeName(String name);


    @Select("select * from demo where id=#{id}")
    public Demo getById(long id);

    @Select("select name from demo where id=#{id}")
    public String getNameById(long id);


}
