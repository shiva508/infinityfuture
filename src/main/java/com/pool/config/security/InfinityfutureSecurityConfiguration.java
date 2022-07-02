package com.pool.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pool.config.security.entrypoint.JwtAuthenticationEntryPoint;
import com.pool.config.security.filter.JwtAuthorizationFilter;
import com.pool.config.security.handler.JwtAccessDeniedHandler;
import com.pool.config.security.jwt.JWTTokenUtility;
import com.pool.service.user.UserService;
import com.pool.util.InfinityFutureSecurityConstant;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class InfinityfutureSecurityConfiguration {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter();
		return jwtAuthorizationFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests().antMatchers(InfinityFutureSecurityConstant.PUBLIC_URLS).permitAll()
				.anyRequest().authenticated().and().exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
		return (web) -> web.ignoring().antMatchers("/h2-console/**").antMatchers("/v2/api-docs/**",
				"/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html/**",
				"/webjars/**");
	}
}
