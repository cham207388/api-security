package com.abc.api.security.rest;

import com.abc.api.security.security.AppUserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
public class StudentAppUserResponse {
	private String id;
	private String username;
	private String name;
	private AppUserRole role;
	private Set<? extends GrantedAuthority> authorities;
}