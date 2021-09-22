package com.abc.apidemo.entity;

import com.abc.apidemo.security.AppUserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class StudentAppUser implements UserDetails {

	@Id
	private String id;
	@Indexed(unique = true)
	private String username;
	private String password;
	private String name;
	private AppUserRole role;
	private Set<? extends GrantedAuthority> authorities;
	private boolean isAccountNonExpired;
	private boolean isAccountNonLocked;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;

	public StudentAppUser(String username, String password, String name, AppUserRole role, Set<? extends GrantedAuthority> authorities, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.authorities = authorities;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
	}

	public StudentAppUser(String username, String password, String name, AppUserRole role) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
	}
}
