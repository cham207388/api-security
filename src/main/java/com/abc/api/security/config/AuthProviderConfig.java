package com.abc.api.security.config;

import com.abc.api.security.service.StudentAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthProviderConfig {

    private final PasswordEncoder passwordEncoder;
    private final StudentAppUserService studentAppUserService;

    /**
     * Database authentication setup:
     * requires:
     * 1- the password encoder
     * 2. A UserDetailsService
     *
     * @return the provider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(studentAppUserService);
        return provider;
    }
}
