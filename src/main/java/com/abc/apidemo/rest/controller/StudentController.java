package com.abc.apidemo.rest.controller;

import com.abc.apidemo.entity.StudentAppUser;
import com.abc.apidemo.rest.StudentAppUserResponse;
import com.abc.apidemo.service.StudentAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {

	@Autowired
	private StudentAppUserService studentAppUserService;

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUserResponse findById(@PathVariable String id) {
		return studentAppUserService.findById(id);
	}

	@GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentAppUser findByUsername(@PathVariable String username) {
		return (StudentAppUser)studentAppUserService.loadUserByUsername(username);
	}
}
