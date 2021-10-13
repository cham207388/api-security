package com.abc.api.security.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User logs in with these fields: username and password
 */
@Getter
@Setter
@NoArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {
	private String username;
	private String password;
}
