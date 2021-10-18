package com.pool.service.role;

import java.util.List;

import com.pool.domain.Role;
import com.pool.model.CommonResponse;

public interface RoleService {
	public Role createNewRole(Role role);

	public List<Role> getRoles();

	public Role updateRole(Role role);

	public CommonResponse deleteRole(Long roleId);
}
