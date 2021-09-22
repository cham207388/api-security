package com.abc.apidemo.repo;

import com.abc.apidemo.entity.StudentAppUser;
import com.abc.apidemo.security.AppUserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAppUserRepository extends MongoRepository<StudentAppUser, String> {

	Optional<StudentAppUser> findByUsername(String username);

	List<StudentAppUser> findAllByRole(AppUserRole role);
}
