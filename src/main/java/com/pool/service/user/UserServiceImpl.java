package com.pool.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.constant.InfinityFutureConstant;
import com.pool.domain.User;
import com.pool.model.CommonResponse;
import com.pool.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	@Transactional
	public User createUser(User user) {
		
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User getUserByUserId(Long userId) {
		Optional<User> optionalUser=userRepository.findById(userId);
		return optionalUser.get();
	}

	@Override
	@Transactional
	public CommonResponse deleteUser(Long userId) {
		userRepository.deleteById(userId);
		return new CommonResponse().setMessageName(InfinityFutureConstant.DELETE_USER_MESSAGE+userId);
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		return userRepository.findAll();
	}

}
