package com.abc.api.security.rest.controller;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.rest.StudentAppUserResponse;
import com.abc.api.security.security.AppUserRole;
import com.abc.api.security.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

	private final StudentAppUserService studentAppUserService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	@GetMapping(path = "/role/{role}",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StudentAppUserResponse> findAllStudents(@PathVariable String role) {
		return studentAppUserService.findAllStudents(AppUserRole.valueOf(role));
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	@GetMapping(path = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findByUsername(@PathVariable String username) {
		return studentAppUserService.findByUsername(username);
	}

	@PreAuthorize("hasAnyAuthority('student:write')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerStudent(@RequestBody StudentAppUser studentAppUser) {
		return studentAppUserService.registerAppUser(studentAppUser);
	}

	@PreAuthorize("hasAnyAuthority('student:write')")
	@PutMapping("/username/{username}/password/{password}")
	public void updateStudent(@PathVariable String username, @PathVariable String password) {
		// find student by username, make sure student is logged-in user
		// update password
		// save student with new details
	}

	@DeleteMapping("/id/{id}")
	@PreAuthorize("hasAnyAuthority('student:write')")
	public void deleteStudent(@PathVariable String id) {
		studentAppUserService.deleteStudent(id);
	}

}
