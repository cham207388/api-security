package com.abc.api.security.security;

import com.abc.api.security.config.AuthProviderConfig;
import com.abc.api.security.config.JwtConfig;
import com.abc.api.security.security.jwt.JwtTokenVerifierFilter;
import com.abc.api.security.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.abc.api.security.security.AppUserRole.STUDENT;

/**
 * This class sets up the security of our application.
 * specifying the endpoints that are allowed by default, those that are allowed with predefined roles
 * and those that require authentication and authorization
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final AuthProviderConfig authProviderConfig;

    /**
     * configure authentication type -basic
     * whitelist some paths - actuator, index, and maybe
     *
     * @param http security
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/actuator/**", "/index.html", "/", "/ping").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(STUDENT.name())
                .anyRequest().authenticated()
                .and();
    }

    /**
     * Configure database authentication provider
     *
     * @param auth Authentication Manager Builder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProviderConfig.daoAuthenticationProvider());
    }
}
