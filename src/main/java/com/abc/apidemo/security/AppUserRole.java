package com.abc.apidemo.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.abc.apidemo.security.AppUserPermission.*;

@Getter
@RequiredArgsConstructor
public enum AppUserRole {
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
	ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

	private final Set<AppUserPermission> permissions;
}
