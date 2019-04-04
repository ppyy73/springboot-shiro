package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper um;

	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		User user = um.getByName(name);
		return user;
	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		return um.getById(id);
	}

}
