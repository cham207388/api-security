package com.abc.api.security.entity;

import com.abc.api.security.security.AppUserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Student {

	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String department;
	private AppUserRole roles;
}
