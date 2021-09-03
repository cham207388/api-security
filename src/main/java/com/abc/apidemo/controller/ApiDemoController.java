package com.abc.apidemo.controller;

import com.abc.apidemo.entity.Student;
import com.abc.apidemo.repo.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/students")
public class ApiDemoController {

	@Autowired
	private ApiRepository apiRepository;

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Student findById(@PathVariable Long id) {
		return apiRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student with id: " + id + " does not exist"));
	}

	@GetMapping(path = "/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Student findByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
		return apiRepository.findByFirstNameAndLastName(firstName, lastName)
				.orElseThrow(() -> new IllegalArgumentException("Student does not exist!"));
	}

	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> findAllStudents() {
		List<Student> students = new ArrayList<>();
		apiRepository.findAll().forEach(students::add);
		return students;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
		return new ResponseEntity<>(apiRepository.save(student), HttpStatus.ACCEPTED);
	}

	@PostMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> saveStudents(@RequestBody List<Student> students) {
		List<Student> stds = new ArrayList<>();
		apiRepository.saveAll(students).forEach(stds::add);

		return stds;
	}
}
