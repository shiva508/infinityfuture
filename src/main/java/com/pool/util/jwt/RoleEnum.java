package com.pool.util.jwt;

public enum RoleEnum {
	ROLE_USER(Authority.USER_AUTHORITIES), 
	ROLE_HR(Authority.HR_AUTHORITIES), 
	ROLE_ADMIN(Authority.ADMIN_AUTHORITIES),
	ROLE_MANAGER(Authority.MANAGER_AUTHORITIES), 
	ROLE_SUPER(Authority.SUPER_AUTHORITIES);

	private String[] authorities;

	RoleEnum(String ...authorities) {
		this.authorities=authorities;
	}

	public String[] getAuthorities() {
		return authorities;
	}
	
	
}
