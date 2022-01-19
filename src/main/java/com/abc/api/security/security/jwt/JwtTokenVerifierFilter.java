package com.abc.api.security.security.jwt;

import com.abc.api.security.config.JwtConfig;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * This is an authorization filter. It verifies the authenticated client and determines if this
 * client is authorized to view the requested resource
 *
 * This class will be triggered anytime a request is headed to a controller
 */
@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

		// get Authorization header value from request
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorization());

		// if it's not in expected format, reject
		if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		// get token by removing "Bearer " prefix from it
		String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), jwtConfig.getEmpty());
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
					.setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes()))
					.build().parseClaimsJws(token);

			Claims body = claimsJws.getBody();
			String username = body.getSubject();
			var authorities = (List<Map<String, String>>) body.get("authorities");

			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities
					.stream()
					.map(m -> new SimpleGrantedAuthority(m.get(jwtConfig.getAuthority())))
					.collect(Collectors.toSet());

			Authentication authentication = new UsernamePasswordAuthenticationToken(
					username,
					null,
					simpleGrantedAuthorities
			);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (JwtException e) {
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		// pass request and response to next filter in the chain or back to client if required
		filterChain.doFilter(request, response);
	}
}
