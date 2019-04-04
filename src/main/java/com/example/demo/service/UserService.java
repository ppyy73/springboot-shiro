package com.example.demo.service;

import com.example.demo.domain.User;


public interface UserService {
	User getUserByName(String name);

	User getById(int id);
}
