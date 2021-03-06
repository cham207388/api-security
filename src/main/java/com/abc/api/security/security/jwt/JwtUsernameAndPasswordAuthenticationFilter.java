package com.abc.api.security.security.jwt;

import com.abc.api.security.config.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


/**
 * This is an authentication filter. An authenticated user will receive a jwt token in the header
 * Authorization = Bearer jwtToken
 * <p>
 * This class will be triggered anytime a user logs in
 */
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    /**
     * This method gets user information. If user is in db, we are good.
     *
     * @param request  containing user information
     * @param response response value having an Authorizatioin header if authenticated
     * @return authentication with header
     * @throws AuthenticationException exception if not authenticated
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest =
                    new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            // if user is in db: check UserDetailsSServiceImpl.loadUserByUsername(username)
            return authenticationManager.authenticate(authentication);

            // otherwise, throw this exception
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add the token to the response header upon successful authentication
     *
     * @param request    the request
     * @param response   the response
     * @param chain      the filterchain
     * @param authResult authentication
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {


        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes()))
                .compact();

        response.addHeader(jwtConfig.getAuthorization(), jwtConfig.getTokenPrefix() + token);
    }
}
