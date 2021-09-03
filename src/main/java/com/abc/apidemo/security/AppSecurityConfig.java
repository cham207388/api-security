package com.abc.apidemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.abc.apidemo.security.AppUserRole.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	/**
	 * configure authentication type -basic
	 * whitelist some paths - actuator, index, and maybe
	 * @param http security
	 * @throws Exception e
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/actuator/*", "/index.html", "/").permitAll()
				.antMatchers("/api/v1/**").hasRole(STUDENT.name())
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails abc = User.builder()
				.username("abc")
				.password(passwordEncoder.encode("password"))
				.roles(STUDENT.name())
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("password"))
				.roles(ADMIN.name())
				.build();

		UserDetails adminTrainee = User.builder()
				.username("admintrainee")
				.password(passwordEncoder.encode("password"))
				.roles(ADMIN_TRAINEE.name())
				.build();
		return new InMemoryUserDetailsManager(abc, admin, adminTrainee);
	}
}
