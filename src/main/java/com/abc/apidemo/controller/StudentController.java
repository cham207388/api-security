package com.abc.apidemo.controller;

import com.abc.apidemo.entity.Student;
import com.abc.apidemo.repo.StudentRepository;
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
	private StudentRepository studentRepository;

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Student findById(@PathVariable Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Student with id: " + id + " does not exist"));
	}

	@GetMapping(path = "/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Student findByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
		return studentRepository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new IllegalArgumentException("Student does not exist!"));
	}
}
