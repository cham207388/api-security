package com.abc.api.security.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Defines the roles for this app users
 * ROLE_ADMIN
 * ROLE_ADMIN_TRAINEE
 * ROLE_STUDENT
 */
@Getter
@RequiredArgsConstructor
public enum AppUserRole {

	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(AppUserPermission.COURSE_READ, AppUserPermission.COURSE_WRITE, AppUserPermission.STUDENT_READ, AppUserPermission.STUDENT_WRITE)),
	ADMIN_TRAINEE(Sets.newHashSet(AppUserPermission.COURSE_READ, AppUserPermission.STUDENT_READ));

	private final Set<AppUserPermission> permissions;

	/**
	 * Makes sure that each role starts with ROLE_ and it's a SimpleGrantedAuthority
	 * @return the permissions of each role
	 */
	public Set<SimpleGrantedAuthority> grantedAuthorities() {
		Set<SimpleGrantedAuthority> permissions = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissions;
	}
}
