package com.abc.api.security.service;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.repository.StudentAppUserRepository;
import com.abc.api.security.rest.StudentAppUserResponse;
import com.abc.api.security.security.AppUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.abc.api.security.security.AppUserRole.*;

@Service
@RequiredArgsConstructor
public class StudentAppUserService implements UserDetailsService {

	private final StudentAppUserRepository studentAppUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return studentAppUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
	}

	public StudentAppUserResponse findByUsername(String username) throws UsernameNotFoundException {
		return studentAppUserRepository.findByUsername(username)
				.map(studentAppUser -> {
					StudentAppUserResponse response = new StudentAppUserResponse();
					BeanUtils.copyProperties(studentAppUser, response);
					return response;
				})
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
	}

	public StudentAppUserResponse findById(String id) {
		return studentAppUserRepository.findById(id)
				.map(studentAppUser -> {
					StudentAppUserResponse response = new StudentAppUserResponse();
					BeanUtils.copyProperties(studentAppUser, response);
					return response;
				}).orElseThrow(() -> new IllegalArgumentException("Student with id: " + id + " does not exist"));
	}

	public String registerAppUser(StudentAppUser studentAppUser) {
		studentAppUser.setPassword(passwordEncoder.encode(studentAppUser.getPassword()));
		String role = studentAppUser.getRole().name();
		switch (role) {
			case "ADMIN":
				studentAppUser.setAuthorities(ADMIN.grantedAuthorities());
				break;
			case "ADMIN_TRAINEE":
				studentAppUser.setAuthorities(ADMIN_TRAINEE.grantedAuthorities());
				break;
			case "STUDENT":
				studentAppUser.setAuthorities(STUDENT.grantedAuthorities());
				break;
			default:
				throw new IllegalArgumentException(String.format("The role: %s does not exist!", role));
		}
		studentAppUser.setAccountNonExpired(true);
		studentAppUser.setAccountNonLocked(true);
		studentAppUser.setEnabled(true);
		studentAppUser.setCredentialsNonExpired(true);
		StudentAppUser appUser = studentAppUserRepository.save(studentAppUser);
		if (appUser.getRole().name().equals("STUDENT")) {
			emailService.sendEmail(appUser);
		}
		return appUser.getUsername();
	}

	public List<StudentAppUserResponse> findAllStudents(AppUserRole role) {
		return studentAppUserRepository.findAllByRole(role)
				.stream()
				.map(studentAppUser -> {
					StudentAppUserResponse response = new StudentAppUserResponse();
					BeanUtils.copyProperties(studentAppUser, response);
					return response;
				})
				.collect(Collectors.toList());
	}

	public void deleteStudent(String id) {
		studentAppUserRepository.deleteById(id);
	}

	public String updatePassword(String username, String password) {
		StudentAppUser appUser = studentAppUserRepository.findByUsername(username)
				.orElseThrow();
		appUser.setPassword(passwordEncoder.encode(password));
		StudentAppUser studentAppUser = studentAppUserRepository.save(appUser);
		return studentAppUser.getUsername() + ": you've reset your password!";
	}
}