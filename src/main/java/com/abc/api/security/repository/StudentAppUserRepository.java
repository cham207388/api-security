package com.abc.api.security.repository;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.security.AppUserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAppUserRepository extends MongoRepository<StudentAppUser, String> {

	Optional<StudentAppUser> findByUsername(String username);

	List<StudentAppUser> findAllByRole(AppUserRole role);
}
