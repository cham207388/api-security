package com.abc.apidemo.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppUserPermission {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");

	private final String permission;

}
