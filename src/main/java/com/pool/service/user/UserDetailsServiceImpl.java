package com.pool.service.user;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.constant.InfinityFutureConstant;
import com.pool.domain.User;
import com.pool.domain.UserPrincipal;
import com.pool.repository.user.UserRepository;
import com.pool.service.cache.LoginAttemptService;

@Service
@Qualifier("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		LOGGER.info("{}", user);
		if (user == null) {
			LOGGER.error(InfinityFutureConstant.USER_NOT_FOUND_BY_USER_NAME);

			throw new UsernameNotFoundException(InfinityFutureConstant.INVALID_CREDENTIALS);
		} else {
			validateLoginattempts(user);
			user.setLastLogInDateDisplay(user.getLastLogInDate());
			user.setLastLogInDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info("USER PRINCIPALE {}", userPrincipal);
			return userPrincipal;
		}
	}

	private void validateLoginattempts(User user) {
		if (user.getIsNotLocked()) {
			if (loginAttemptService.isUserExcededLoginAttempts(user.getUsername())) {
				user.setIsNotLocked(false);
			} else {
				user.setIsNotLocked(true);
			}
		} else {
			loginAttemptService.evictLoggedUser(user.getUsername());
		}
	}

}
