package com.abc.apidemo.bootstrap;

import com.abc.apidemo.entity.StudentAppUser;
import com.abc.apidemo.repo.StudentAppUserRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import static com.abc.apidemo.security.AppUserRole.*;

@Component
@RequiredArgsConstructor
public class BootstrapUsers implements CommandLineRunner {

	private final StudentAppUserRepository studentAppUserRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public void run(String... args) {
		//initializeDBWithAppUsers();
	}

	private void initializeDBWithAppUsers() {

		studentAppUserRepository.save(new StudentAppUser("admin", passwordEncoder.encode("password"), "Alhagie Bai Cham", ADMIN, ADMIN.grantedAuthorities(), true, true, true, true));
		studentAppUserRepository.save(new StudentAppUser("admin_trainee", passwordEncoder.encode("password"), "Alhagie Bai Cham", ADMIN_TRAINEE, ADMIN_TRAINEE.grantedAuthorities(), true, true, true, true));
		studentAppUserRepository.save(new StudentAppUser("student", passwordEncoder.encode("password"), "Alhagie Bai Cham", STUDENT, STUDENT.grantedAuthorities(), true, true, true, true));

	}
}
