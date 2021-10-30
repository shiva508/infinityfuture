package com.pool.util.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pool.constant.InfinityFutureSecurityConstant;
import com.pool.domain.UserPrincipal;
import com.pool.model.Login;

@Component
public class JWTTokenUtility {
	@Value("${jwt.secret}")
	private String secret;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String generateJwtToken(UserPrincipal userPrincipal) {
		String claims[]=extractClaimsFromUser(userPrincipal);
		String jwtToken=JWT.create()
						   .withIssuer(InfinityFutureSecurityConstant.GET_ARRAYS_LLC)
						   .withAudience(InfinityFutureSecurityConstant.GET_ARRAYS_ADMINISTRATION)
						   .withIssuedAt(new Date(System.currentTimeMillis()))
						   .withSubject(userPrincipal.getUsername())
						   .withArrayClaim(InfinityFutureSecurityConstant.AUTHORITIES, claims)
						   .withExpiresAt(new Date(System.currentTimeMillis()+InfinityFutureSecurityConstant.EXPIRATION_TIME))
						   .sign(generateAlgorithm(secret));
						   
		return jwtToken;
	}

	public String[] extractClaimsFromUser(UserPrincipal userPrincipal) {
		List<String> cliamsList=userPrincipal.getAuthorities().stream().map(auth->auth.getAuthority()).collect(Collectors.toList());
		return cliamsList.stream().toArray(String[]::new);
	}
	
	public Algorithm generateAlgorithm(String secret) {
		Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
		return algorithm;
	}
	
	public List<GrantedAuthority> extractAuthoritiesFromToken(String jwtToken){
		String[] claims=extractClaimsFromJwtToken(jwtToken);
		List<String> claimList=Arrays.asList(claims);
		return claimList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public String[] extractClaimsFromJwtToken(String jwtToken) {
		JWTVerifier jwtVerifier=getJwtVerifier();
		return jwtVerifier.verify(jwtToken).getClaim(InfinityFutureSecurityConstant.AUTHORITIES).asArray(String.class);
	}

	public JWTVerifier getJwtVerifier() {
		JWTVerifier jwtVerifier;
		try {
			Algorithm algorithm = generateAlgorithm(secret);
			jwtVerifier=JWT.require(algorithm).withIssuer(InfinityFutureSecurityConstant.GET_ARRAYS_LLC).build();
		} catch (JWTVerificationException jwtVerificationException) {
			throw new JWTVerificationException(InfinityFutureSecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
		}
		return jwtVerifier;
	}
	
	public Authentication buildAuthenticationObject(String username,List<GrantedAuthority> grantedAuthorities,HttpServletRequest httpServletRequest) {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
		return authenticationToken;
		
	}
	
	public boolean isTokenValid(String username,String token) {
		JWTVerifier jwtVerifier=getJwtVerifier();
		return StringUtils.isNotEmpty(username) && isJwtTokenExpaired(jwtVerifier,token);
	}

	public boolean isJwtTokenExpaired(JWTVerifier jwtVerifier,String token) {
		Date expairationDate=jwtVerifier.verify(token).getExpiresAt();
		return expairationDate.before(new Date());
	}
	
	public String extractSubjectFromToken(String jwtToken) {
		JWTVerifier jwtVerifier=getJwtVerifier();
		return jwtVerifier.verify(jwtToken).getSubject();
	}

	public void authenticate(Login login) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		
	}

	public HttpHeaders generateJwtHeaders(UserPrincipal userPrincipal) {
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add(InfinityFutureSecurityConstant.JWT_TOKEN_HEADER, generateJwtToken(userPrincipal));
		return httpHeaders;
	}
	
	public String generateJwtTokenValue(UserPrincipal userPrincipal) {
		return generateJwtToken(userPrincipal);
	}
}
