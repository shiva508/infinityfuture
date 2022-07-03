package com.pool.service.user;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.pool.domain.User;
import com.pool.model.CommonResponse;

public interface UserService {
	public User createUser(User user);

	public User updateUser(User user);

	public User getUserByUserId(Long userId);

	public CommonResponse deleteUser(Long userId);

	public List<User> getUsers();

	public CommonResponse resetPassword(String username,String newPassword);
	
	public CommonResponse updatePrifileImage(String username,MultipartFile profileEmage);
}
