package com.pool.service.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pool.config.email.handler.EmailTriggerEvent;
import com.pool.domain.User;
import com.pool.model.CommonResponse;
import com.pool.model.email.EmailModel;
import com.pool.model.exception.EmailExistException;
import com.pool.model.security.RoleEnum;
import com.pool.repository.user.UserRepository;
import com.pool.util.InfinityFutureConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final ApplicationEventPublisher publisher;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			ApplicationEventPublisher publisher) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.publisher = publisher;
	}

	@Override
	@Transactional
	public User createUser(User user) {
		
		User isUserExist=userRepository.findByEmail(user.getEmail());
		if(null !=isUserExist) {
			log.error("User aleready exist with email:");
			throw new EmailExistException(InfinityFutureConstant.EMAIL_EXITS+user.getEmail());
		}else {
			user.setUsername(user.getEmail());
			user.setJoinDate(new Date());
			String password=user.getPassword();
			user.setPassword(bCryptPasswordEncoder.encode(password));
			user.setIsNotLocked(true);
			user.setIsActive(true);
			user.setRoleTest(RoleEnum.ROLE_USER.name());
			user.setProfileImage(randomProfile());
			user.setAuthorities(RoleEnum.ROLE_USER.getAuthorities());
			publisher.publishEvent(new EmailTriggerEvent(new EmailModel(user.getUsername(), user.getUsername(), user.getPassword())));
			return userRepository.save(user);
		}
		 

	}

	public String randomProfile() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toUriString();
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User getUserByUserId(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.get();
	}

	@Override
	@Transactional
	public CommonResponse deleteUser(Long userId) {
		userRepository.deleteById(userId);
		return new CommonResponse().setMessageName(InfinityFutureConstant.DELETE_USER_MESSAGE + userId);
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		return userRepository.findAll();
	}
}
