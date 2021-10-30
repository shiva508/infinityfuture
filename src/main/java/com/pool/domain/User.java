package com.pool.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_USER")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long userId;
	private String ifuId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String profileImage;
	private Date lastLogInDate;
	private Date lastLogInDateDisplay;
	private Date joinDate;
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "TBL_USER_ROLE", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
	private List<Role> roles;
	private String[] authorities;
	private Boolean isActive;
	private Boolean isNotLocked;
	private String roleTest;

	public User() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIfuId() {
		return ifuId;
	}

	public void setIfuId(String ifuId) {
		this.ifuId = ifuId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Date getLastLogInDate() {
		return lastLogInDate;
	}

	public void setLastLogInDate(Date lastLogInDate) {
		this.lastLogInDate = lastLogInDate;
	}

	public Date getLastLogInDateDisplay() {
		return lastLogInDateDisplay;
	}

	public void setLastLogInDateDisplay(Date lastLogInDateDisplay) {
		this.lastLogInDateDisplay = lastLogInDateDisplay;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsNotLocked() {
		return isNotLocked;
	}

	public void setIsNotLocked(Boolean isNotLocked) {
		this.isNotLocked = isNotLocked;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleTest() {
		return roleTest;
	}

	public void setRoleTest(String roleTest) {
		this.roleTest = roleTest;
	}

}
