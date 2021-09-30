package com.abc.apidemo.rest.controller;

import com.abc.apidemo.rest.StudentAppUserResponse;
import com.abc.apidemo.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentAppUserService studentAppUserService;

	@PreAuthorize("hasAnyRole('ROLE_STUDENT')")
	@GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findById(@PathVariable String id) {
		return studentAppUserService.findById(id);
	}

	@PreAuthorize("#username == authentication.principal")
	@GetMapping(path = "/username", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findByUsername(@Param("username") String username) {
		return studentAppUserService.findByUsername(username);
	}

	@PreAuthorize("#username == authentication.principal")
	@PutMapping(path = "/username/{username}/password/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String updatePassword(@PathVariable String username, @PathVariable String password) {
		return studentAppUserService.updatePassword(username, password);
	}
}
