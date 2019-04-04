package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where name =#{name}")
	public User getByName(String name);

	@Select("select * from user where id = #{id}")
	public User getById(int id);
}
