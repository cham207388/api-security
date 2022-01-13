package com.abc.api.security.rest.controller.impl;

import com.abc.api.security.rest.controller.StudentController;
import com.abc.api.security.rest.response.StudentAppUserResponse;
import com.abc.api.security.service.StudentAppUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Student Portal API")
@RestController
@RequestMapping(path = "/api/v1/students")
@RequiredArgsConstructor
public class StudentControllerImpl implements StudentController {

	private final StudentAppUserService studentAppUserService;

	@Override
	@PreAuthorize("#id == authentication.principal")
	@GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findById(@PathVariable String id) {
		return studentAppUserService.findById(id);
	}

	@Override
	@Cacheable(value = "studentAppUser", key = "#username")
	@PreAuthorize("#username == authentication.principal")
	@GetMapping(path = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findByUsername(@PathVariable String username) {
		return studentAppUserService.findByUsername(username);
	}

	@Override
	@CachePut(value = "studentAppUser", key = "#username")
	@PreAuthorize("#username == authentication.principal")
	@PutMapping(path = "/username/{username}/password/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse updateStudentAppUserPasswordByUsername(@PathVariable String username, @PathVariable String password) {
		return studentAppUserService.updatePassword(username, password);
	}
}
