package com.abc.api.security.bootstrap;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.repository.StudentAppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static com.abc.api.security.security.AppUserRole.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapUsers {

	private final StudentAppUserRepository studentAppUserRepository;
	private final PasswordEncoder passwordEncoder;


	@PostConstruct
	public void initializeDBWithAppUsers() {
		log.info("@PostConstruct => Intializing DB: count = {}", studentAppUserRepository.count());
		studentAppUserRepository.save(new StudentAppUser("admin", passwordEncoder.encode("password"), "admin@email.net", "Adminis Trator", ADMIN, ADMIN.grantedAuthorities(), true, true, true, true));
		studentAppUserRepository.save(new StudentAppUser("admintrainee", passwordEncoder.encode("password"), "admin@email.net", "Admin Assistant", ADMIN_TRAINEE, ADMIN_TRAINEE.grantedAuthorities(), true, true, true, true));
		studentAppUserRepository.save(new StudentAppUser("student", passwordEncoder.encode("password"), "student@email.net", "Student Student", STUDENT, STUDENT.grantedAuthorities(), true, true, true, true));
		log.info("@PostConstruct => Loaded DB: count = {}", studentAppUserRepository.count());
	}

	@PreDestroy
	public void emptyDb(){
		log.info("Emptying DB");
		studentAppUserRepository.deleteAll();
		log.info("@PreDestroy => Empty DB: count = {}", studentAppUserRepository.count());
	}
}
