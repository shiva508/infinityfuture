package com.pool.service.role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.domain.Role;
import com.pool.model.CommonResponse;
import com.pool.repository.role.RoleRepository;
import com.pool.util.InfinityFutureConstant;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public Role createNewRole(Role role) {

		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public List<Role> getRoles() {

		return roleRepository.findAll();
	}

	@Override
	@Transactional
	public Role updateRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public CommonResponse deleteRole(Long roleId) {
		roleRepository.deleteById(roleId);
		return new CommonResponse().setMessageName(InfinityFutureConstant.DELETE_ROLE_MESSAGE+roleId);
	}

}
