package com.example.m183project.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("Handling authorization header '{}' for resource {}.", header, request.getRequestURL());

        if(header != null) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
                try {
                    Authentication authentication = userAuthProvider.validateToken(authElements[1]);
                    log.info("Authenticated user: {}", authentication.getPrincipal());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (TokenExpiredException te) {
                    SecurityContextHolder.clearContext();
                    request.setAttribute("expired", te.getMessage());
                    log.error("Token expired: {}", te.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
