package com.pool.service.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.pool.constant.InfinityFutureConstant;

@Service
public class LoginAttemptService {

	private LoadingCache<String,Integer> loginLoadingCache;

	public LoginAttemptService() {
		super();
		loginLoadingCache=CacheBuilder
				.newBuilder()
				.expireAfterAccess(15,TimeUnit.MINUTES)
				.maximumSize(100)
				.build(new CacheLoader<String,Integer>(){
					@Override
					public Integer load(String key) throws Exception {
						return 0;
					}
				});
	}
	
	public void evictLoggedUser(String username) {
		loginLoadingCache.invalidate(username);
	}
	
	public void addUserToAttemotLoginCache(String username) {
		int attempts=0;
		try {
			if(null !=loginLoadingCache.get(username)) {
				attempts=loginLoadingCache.get(username)+InfinityFutureConstant.NUMBER_OF_LOGIN_ATTEMPTS_INCREAMENT;
				loginLoadingCache.put(username, attempts);
			}else {
				loginLoadingCache.put(username, attempts);
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public boolean isUserExcededLoginAttempts(String username) {
		try {
			return loginLoadingCache.get(username)>=InfinityFutureConstant.NUMBER_OF_LOGIN_ATTEMPTS;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
