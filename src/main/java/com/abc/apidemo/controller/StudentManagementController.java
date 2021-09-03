package com.abc.apidemo.controller;

import com.abc.apidemo.entity.Student;
import com.abc.apidemo.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {


	private final StudentRepository studentRepository;

	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> findAllStudents() {
		List<Student> students = new ArrayList<>();
		studentRepository.findAll().forEach(students::add);
		return students;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Student registerStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@PostMapping(path = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Student> saveStudents(@RequestBody List<Student> students) {
		List<Student> stds = new ArrayList<>();
		studentRepository.saveAll(students).forEach(stds::add);

		return stds;
	}

	@PutMapping("/{id}/{department}")
	public void updateStudent(@PathVariable Long id, @PathVariable String department) {

		System.out.println("Student updated");
		Optional<Student> studentOptional = studentRepository.findById(id);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			student.setDepartment(department);
			studentRepository.save(student);
		} else {
			throw new IllegalArgumentException("Student with id: " + id + " not found!");
		}
	}

	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Long id) {
		studentRepository.deleteById(id);
	}
}
