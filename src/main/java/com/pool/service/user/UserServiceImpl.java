package com.pool.service.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.domain.User;
import com.pool.domain.UserPrincipal;
import com.pool.model.CommonResponse;
import com.pool.model.exception.EmailExistException;
import com.pool.model.security.Authority;
import com.pool.model.security.RoleEnum;
import com.pool.repository.user.UserRepository;
import com.pool.service.email.InfinityFutureEmailService;
import com.pool.util.InfinityFutureConstant;

@Service
public class UserServiceImpl implements UserService {
	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private InfinityFutureEmailService infinityFutureEmailService;
	
	@Override
	@Transactional
	public User createUser(User user) {
		
		User isUserExist=userRepository.findByEmail(user.getEmail());
		if(null !=isUserExist) {
			throw new EmailExistException(InfinityFutureConstant.EMAIL_EXITS+user.getEmail());
		}else {
			user.setUsername(user.getEmail());
			user.setJoinDate(new Date());
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setIsNotLocked(true);
			user.setIsActive(true);
			user.setRoleTest(RoleEnum.ROLE_USER.name());
			user.setAuthorities(RoleEnum.ROLE_USER.getAuthorities());
			//infinityFutureEmailService.sendRegistrationConfirmation(user);
			return userRepository.save(user);
		}
		 

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
