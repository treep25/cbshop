package com.cbshop.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private void authenticateToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {
        try {
            Cookie[] allCookies = request.getCookies();
            if (allCookies != null) {
                Cookie session =
                        Arrays.stream(allCookies).filter(x -> x.getName().equals("JSESSIONID"))
                                .findFirst().orElse(null);
                if (session != null) {
                    String email = (String) request.getSession().getAttribute("Email");

                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                        authenticateToken(userDetails, request);

                    }
                }
            }
            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            response.setHeader("error", ex.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());

            Map<String, String> error = new HashMap<>();

            error.put("error_message", ex.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }


}
