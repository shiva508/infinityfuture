package com.pool.service.user;

import java.util.List;
import com.pool.domain.User;
import com.pool.model.CommonResponse;

public interface UserService {
	public User createUser(User user);

	public User updateUser(User user);

	public User getUserByUserId(Long userId);

	public CommonResponse deleteUser(Long userId);

	public List<User> getUsers();
}
