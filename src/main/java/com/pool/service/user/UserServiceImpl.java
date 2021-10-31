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
import com.pool.constant.InfinityFutureConstant;
import com.pool.domain.User;
import com.pool.domain.UserPrincipal;
import com.pool.model.CommonResponse;
import com.pool.model.exception.EmailExistException;
import com.pool.repository.user.UserRepository;
import com.pool.service.email.InfinityFutureEmailService;
import com.pool.util.jwt.Authority;
import com.pool.util.jwt.RoleEnum;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
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

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		LOGGER.info("{}", user);
		if (user == null) {
			LOGGER.error(InfinityFutureConstant.USER_NOT_FOUND_BY_USER_NAME);
			throw new UsernameNotFoundException(InfinityFutureConstant.USER_NOT_FOUND_BY_USER_NAME);
		} else {
			user.setLastLogInDateDisplay(user.getLastLogInDate());
			user.setLastLogInDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info("USER PRINCIPALE {}",userPrincipal);
			return userPrincipal;
		}
	}

}
