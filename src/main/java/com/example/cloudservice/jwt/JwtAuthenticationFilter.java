package com.example.cloudservice.jwt;

import com.example.cloudservice.service.security.DefaultUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER_AUTHORIZATION = "Auth-Token";
    private static final String PREFIX_BEARER = "Bearer ";

    private final JwtGenerator tokenGenerator;
    private final DefaultUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var token = getJWTFromRequest(request);
        if (nonNull(token) && tokenGenerator.validateToken(token)) {
            final var username = tokenGenerator.getUsernameFromJWT(token);
            final var userDetails = customUserDetailsService.loadUserByUsername(username);
            final var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        final var bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        return hasText(bearerToken) && bearerToken.startsWith(PREFIX_BEARER) ?
                bearerToken.substring(7) :
                null;
    }
}
