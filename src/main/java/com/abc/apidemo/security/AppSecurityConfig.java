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
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/actuator/*", "/index.html", "/").permitAll()
				.antMatchers("/api/v1/**").hasRole(STUDENT.name())
//				.antMatchers(HttpMethod.DELETE,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.POST,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.PUT,"/management/api/**").hasAnyAuthority(COURSE_WRITE.getPermission())
//				.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
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
