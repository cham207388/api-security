package com.abc.apidemo.security;

import com.abc.apidemo.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

import static com.abc.apidemo.security.AppUserRole.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final StudentAppUserService studentAppUserService;

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
				.antMatchers("/actuator/*", "/index.html", "/", "/ping").permitAll()
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
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(studentAppUserService);
		return provider;
	}
}
