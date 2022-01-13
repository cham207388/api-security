package com.abc.api.security.rest.controller.impl;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.rest.controller.StudentManagementController;
import com.abc.api.security.rest.response.StudentAppUserResponse;
import com.abc.api.security.security.AppUserRole;
import com.abc.api.security.service.StudentAppUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Student Manage API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementControllerImpl implements StudentManagementController {

	private final StudentAppUserService studentAppUserService;

	@Override
	@Cacheable(value = "studentAppUsers")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	@GetMapping(path = "/role/{role}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StudentAppUserResponse> findAllStudents(@PathVariable String role) {
		return studentAppUserService.findAllStudents(AppUserRole.valueOf(role));
	}

	@Override
	@Cacheable(cacheNames = "studentAppUser", key = "#username")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	@GetMapping(path = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findByUsername(@PathVariable String username) {
		return studentAppUserService.findByUsername(username);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('student:write')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerStudent(@RequestBody StudentAppUser studentAppUser) {
		return studentAppUserService.registerAppUser(studentAppUser);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('student:write')")
	@PutMapping("/username/{username}/password/{password}")
	public StudentAppUserResponse updateStudent(@PathVariable String username, @PathVariable String password) {
		return studentAppUserService.updatePassword(username, password);
	}

	@Override
	@CacheEvict(value = "studentAppUsers", allEntries = true)
	@DeleteMapping("/username/{username}")
	@PreAuthorize("hasAnyAuthority('student:write')")
	public void deleteStudent(@PathVariable String username) {
		studentAppUserService.deleteStudentByUsername(username);
	}

}
