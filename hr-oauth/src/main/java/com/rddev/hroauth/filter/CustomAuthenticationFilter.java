package com.rddev.hroauth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rddev.hroauth.entities.Role;
import com.rddev.hroauth.entities.User;
import com.rddev.hroauth.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Attempting authentication");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getParameter("email"), request.getParameter("password"));
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        log.info("Authentication successful for user {}", user.getEmail());

        String token = JwtUtils.generateToken(user.getEmail(), user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
        String refreshToken = JwtUtils.generateRefreshToken(user.getEmail());

//        response.setHeader("token", "Bearer "+token);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("token", token);
        tokens.put("refresh_token", refreshToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}
