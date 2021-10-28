package com.abc.api.security.rest.controller;

import com.abc.api.security.rest.response.StudentAppUserResponse;
import com.abc.api.security.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

	private final StudentAppUserService studentAppUserService;

	@PreAuthorize("#id == authentication.principal")
	@GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findById(@PathVariable String id) {
		return studentAppUserService.findById(id);
	}

	@Cacheable(value = "studentAppUser", key = "#username")
	@PreAuthorize("#username == authentication.principal")
	@GetMapping(path = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findByUsername(@PathVariable String username) {
		return studentAppUserService.findByUsername(username);
	}

	@CachePut(value = "studentAppUser", key = "#username")
	@PreAuthorize("#username == authentication.principal")
	@PutMapping(path = "/username/{username}/password/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse updateStudentAppUserPasswordByUsername(@PathVariable String username, @PathVariable String password) {
		return studentAppUserService.updatePassword(username, password);
	}
}
