package com.abc.apidemo.rest.controller;

import com.abc.apidemo.entity.StudentAppUser;
import com.abc.apidemo.rest.StudentAppUserResponse;
import com.abc.apidemo.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StudentAppUserResponse> findAllStudents() {
		return studentAppUserService.findAllStudents();
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
	@GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findByUsername(@PathVariable String username) {

		StudentAppUserResponse response = new StudentAppUserResponse();
		BeanUtils.copyProperties(studentAppUserService.loadUserByUsername(username), response);
		return response;
	}

	@PreAuthorize("hasAnyAuthority('student:write')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String registerStudent(@RequestBody StudentAppUser student) {
		return studentAppUserService.registerAppUser(student);
	}

	@PreAuthorize("hasAnyAuthority('student:write')")
	@PutMapping("/{username}/{department}")
	public void updateStudent(@PathVariable String username, @PathVariable String department) {

		//studentAppUserService.updateStudent();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('student:write')")
	public void deleteStudent(@PathVariable String id) {
		studentAppUserService.deleteStudent(id);
	}

	@PostMapping
	public String registerAppUser(@RequestBody StudentAppUser studentAppUser) {
		return studentAppUserService.registerAppUser(studentAppUser);
	}
}
