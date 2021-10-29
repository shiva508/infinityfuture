package com.pool.util.jwt.filter;

import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.pool.constant.InfinityFutureSecurityConstant;
import com.pool.util.jwt.JWTTokenUtility;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTTokenUtility jwtTokenUtility;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equalsIgnoreCase(InfinityFutureSecurityConstant.OPTIONS_HTTP_METHOD)) {
			response.setStatus(HttpStatus.OK.value());
		}else {
			String authorizationHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
			if(null ==authorizationHeader || !authorizationHeader.startsWith(InfinityFutureSecurityConstant.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String jwtToken=authorizationHeader.substring(InfinityFutureSecurityConstant.TOKEN_PREFIX.length());
			String username=jwtTokenUtility.extractSubjectFromToken(jwtToken);
			if(jwtTokenUtility.isTokenValid(username, jwtToken) && SecurityContextHolder.getContext().getAuthentication() ==null) {
				List<GrantedAuthority>  authorities=jwtTokenUtility.extractAuthoritiesFromToken(jwtToken);
				Authentication authentication=jwtTokenUtility.buildAuthenticationObject(username, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);

	}

}
