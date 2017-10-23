package com.chen.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chen.entity.User;
import com.chen.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	
	public long findIDs(Integer s){
		
		List<User> list = userRepository.findAll();
		long sum = list.stream().map(x->x.getId()).reduce(Long::sum).get();
		
		return sum;
	}
	
}
