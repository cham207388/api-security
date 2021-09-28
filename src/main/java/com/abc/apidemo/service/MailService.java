package com.abc.apidemo.service;

import com.abc.apidemo.config.MailConfig;
import com.abc.apidemo.entity.StudentAppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;
	private final MailConfig mailConfig;

	public void sendEmail(StudentAppUser appUser) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(mailConfig.getFrom());
			mailMessage.setTo(appUser.getEmail());
			mailMessage.setText("Please login to Student Portal with this password: password"
					+ "and reset immediately!\n"
					+ "at <a href=\"http://localhost:8080/api/v1/students/"
					+ appUser.getUsername() + "\">reset password</a>");
			mailMessage.setSubject("Password reset!");

			javaMailSender.send(mailMessage);
			log.info("Email is sent to user: {}", appUser.getUsername());
		} catch (Exception exception) {
			log.info(exception.getMessage());
		}
	}
}
