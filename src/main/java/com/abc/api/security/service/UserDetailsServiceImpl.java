package com.abc.api.security.service;

import com.abc.api.security.repository.StudentAppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Alhagie Bai Cham
 * @date 2/28/22
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final StudentAppUserRepository studentAppUserRepository;

    /**
     * This method is call when user tries to authenticate
     * Once you attempt login in with username and password, this method will be call after
     * JwtUsernameAndPasswordAuthenticationFilter.attemptAuthentication(request, response)
     *
     * @param username username
     * @return UserDetails having username and password
     * @throws UsernameNotFoundException user does not exist exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentAppUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
