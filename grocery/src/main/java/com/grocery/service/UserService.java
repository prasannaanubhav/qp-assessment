package com.grocery.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.grocery.entity.UserEntity;
import com.grocery.repo.UserRepository;

public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserEntity registerNewUserAccount(UserEntity userEntity) {

		return userRepository.save(userEntity);
	}

}
