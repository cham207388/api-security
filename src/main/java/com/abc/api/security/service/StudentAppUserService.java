package com.abc.api.security.service;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.repository.StudentAppUserRepository;
import com.abc.api.security.rest.response.StudentAppUserResponse;
import com.abc.api.security.security.AppUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.abc.api.security.security.AppUserRole.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentAppUserService {

    private final StudentAppUserRepository studentAppUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public StudentAppUserResponse findByUsername(String username) throws UsernameNotFoundException {
        log.info("   ==== Not from cache ====");
        return studentAppUserRepository.findByUsername(username)
                .map(getStudentAppUserResponseFunction())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    public StudentAppUserResponse findById(String id) {
        return studentAppUserRepository.findById(id)
                .map(getStudentAppUserResponseFunction()).orElseThrow(() -> new IllegalArgumentException("Student with id: " + id + " does not exist"));
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
        log.info("   === not from cache === ");
        return studentAppUserRepository.findAllByRole(role)
                .stream()
                .map(getStudentAppUserResponseFunction())
                .collect(Collectors.toList());
    }

    public void deleteStudentByUsername(String username) {
        studentAppUserRepository.deleteByUsername(username);
    }

    public StudentAppUserResponse updatePassword(String username, String password) {
        StudentAppUser appUser = studentAppUserRepository.findByUsername(username)
                .orElseThrow();
        appUser.setPassword(passwordEncoder.encode(password));
        StudentAppUser studentAppUser = studentAppUserRepository.save(appUser);
        return getStudentAppUserResponseFunction().apply(studentAppUser);
    }

    private Function<StudentAppUser, StudentAppUserResponse> getStudentAppUserResponseFunction() {
        return studentAppUser -> {
            StudentAppUserResponse response = new StudentAppUserResponse();
            BeanUtils.copyProperties(studentAppUser, response);
            return response;
        };
    }
}
