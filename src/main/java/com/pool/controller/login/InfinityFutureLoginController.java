package com.pool.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pool.config.security.jwt.JWTTokenUtility;
import com.pool.domain.UserPrincipal;
import com.pool.model.Login;

@RestController
@RequestMapping("/auth")
public class InfinityFutureLoginController {

	@Autowired
	private JWTTokenUtility jWTTokenUtility;
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@PostMapping("/login")
	public String login(@RequestBody Login login) {
		jWTTokenUtility.authenticate(login);
		UserPrincipal userPrincipal=(UserPrincipal) userDetailsService.loadUserByUsername(login.getUserName());
		
		if(null ==userPrincipal) {
			
		}
		
		return jWTTokenUtility.generateJwtTokenValue(userPrincipal);
	}
}
