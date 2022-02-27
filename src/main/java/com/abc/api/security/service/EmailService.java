package com.abc.api.security.service;

import com.abc.api.security.config.MailConfig;
import com.abc.api.security.entity.StudentAppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Sends email
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
	private final MailConfig mailConfig;
	private final JavaMailSender mailSender;

	public void sendEmail(StudentAppUser appUser) {

			try {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom(mailConfig.getFrom());
				mailMessage.setTo(appUser.getEmail());
				mailMessage.setText(appUser.getName()+"Please login to Student Portal and reset your password immediately!\n");
				mailMessage.setSubject("Password reset!");

				mailSender.send(mailMessage);
				log.info("Email is sent to user: {}", appUser.getUsername());
			} catch (Exception exception) {
				log.info(exception.getMessage());
			}
	}
}
