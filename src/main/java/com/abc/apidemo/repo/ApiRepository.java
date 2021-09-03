package com.abc.apidemo.repo;

import com.abc.apidemo.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApiRepository extends CrudRepository<Student, Long> {

	Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
}
