package com.abc.apidemo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.concurrent.TimeUnit;

import static com.abc.apidemo.security.AppUserRole.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/actuator/*", "/index.html", "/").permitAll()
				.antMatchers("/api/v1/**").hasRole(STUDENT.name())
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/courses", true)
				.and()
				.rememberMe()
					.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
					.key("somethingverysecured")
				.and()
				.logout()
					.logoutUrl("/logout")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID", "remember-me")
					.logoutSuccessUrl("/login")
		;
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails student = User.builder()
				.username("student")
				.password(passwordEncoder.encode("password"))
				//.roles(STUDENT.name())
				.authorities(STUDENT.grantedAuthorities())
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("password"))
				//.roles(ADMIN.name())
				.authorities(ADMIN.grantedAuthorities())
				.build();

		UserDetails trainee = User.builder()
				.username("trainee")
				.password(passwordEncoder.encode("password"))
				//.roles(ADMIN_TRAINEE.name())
				.authorities(ADMIN_TRAINEE.grantedAuthorities())
				.build();
		return new InMemoryUserDetailsManager(student, admin, trainee);
	}
}
