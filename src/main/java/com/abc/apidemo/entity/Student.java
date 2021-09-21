package com.abc.apidemo.entity;

import com.abc.apidemo.security.AppUserRole;
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
