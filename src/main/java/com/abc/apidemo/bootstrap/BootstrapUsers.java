package com.abc.apidemo.bootstrap;

import com.abc.apidemo.entity.StudentAppUser;
import com.abc.apidemo.repo.StudentAppUserRepository;
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
		initializeDBWithAppUsers();
	}

	private void initializeDBWithAppUsers() {

		studentAppUserRepository.save(new StudentAppUser("abcham", passwordEncoder.encode("password"),"test@test.com", "Alhagie Bai Cham", ADMIN, ADMIN.grantedAuthorities(), true, true, true, true));
		studentAppUserRepository.save(new StudentAppUser("hcham", passwordEncoder.encode("password"), "test@test.com","Horeja Cham", ADMIN_TRAINEE, ADMIN_TRAINEE.grantedAuthorities(), true, true, true, true));
		studentAppUserRepository.save(new StudentAppUser("asimaha", passwordEncoder.encode("password"), "cham.abc1@gmail.com","Abubacarr Simaha", STUDENT, STUDENT.grantedAuthorities(), true, true, true, true));
	}
}
