package com.abc.api.security.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Permissions for users of the student app portal
 */
@Getter
@RequiredArgsConstructor
public enum AppUserPermission {

	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");

	private final String permission;
}
