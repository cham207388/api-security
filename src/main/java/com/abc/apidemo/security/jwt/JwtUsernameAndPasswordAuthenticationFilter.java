package com.abc.apidemo.security.jwt;

import com.abc.apidemo.util.Utility;
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

import static com.abc.apidemo.util.Utility.*;

/**
 * This filter validates and sends jwt token to client to use for subsequent requests
 */
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		try {
			UsernameAndPasswordAuthenticationRequest authenticationRequest =
					new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(),
					authenticationRequest.getPassword()
			);

			return authenticationManager.authenticate(authentication);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
	                                        HttpServletResponse response,
	                                        FilterChain chain,
	                                        Authentication authResult) {


		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new java.util.Date())
				.setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))
				.signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
				.compact();

		response.addHeader(AUTHORIZATION, BEARER + token);
	}
}
