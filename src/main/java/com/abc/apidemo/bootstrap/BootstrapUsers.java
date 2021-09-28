package com.abc.apidemo.bootstrap;

import com.abc.apidemo.entity.StudentAppUser;
import com.abc.apidemo.repo.StudentAppUserRepository;
import com.abc.apidemo.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import static com.abc.apidemo.security.AppUserRole.*;

@Component
@RequiredArgsConstructor
public class BootstrapUsers implements CommandLineRunner {

	private final StudentAppUserService service;
	private final StudentAppUserRepository repository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public void run(String... args) {
		initializeDBWithAppUsers();
	}

	private void initializeDBWithAppUsers() {
		repository.deleteAll();

		service.registerAppUser(new StudentAppUser("admin", passwordEncoder.encode("password"),"test@test.com", "Alhagie Bai Cham", ADMIN, ADMIN.grantedAuthorities(), true, true, true, true));
		service.registerAppUser(new StudentAppUser("hcham", passwordEncoder.encode("password"), "test@test.com","Horeja Cham", ADMIN_TRAINEE, ADMIN_TRAINEE.grantedAuthorities(), true, true, true, true));
		service.registerAppUser(new StudentAppUser("asimaha", passwordEncoder.encode("password"), "cham.abc1@gmail.com","Abubacarr Simaha", STUDENT, STUDENT.grantedAuthorities(), true, true, true, true));
	}
}
