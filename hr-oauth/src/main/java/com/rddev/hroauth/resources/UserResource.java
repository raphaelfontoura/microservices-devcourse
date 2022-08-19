package com.rddev.hroauth.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rddev.hroauth.entities.Role;
import com.rddev.hroauth.entities.User;
import com.rddev.hroauth.services.UserService;
import com.rddev.hroauth.utils.JwtUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserResource {

    private final UserService service;

    @GetMapping("/search")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        try {
            User user = service.findByEmail(email);
            return ResponseEntity.ok(user);
        } catch (FeignException | IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String username = JwtUtils.getUsername(authorizationHeader);
                User user = service.findByEmail(username);
                String token = JwtUtils.generateToken(user.getEmail(), user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));

                response.addHeader(AUTHORIZATION, "Bearer " + token);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("token", token);
                tokens.put("refresh_token", JwtUtils.generateRefreshToken(user.getEmail()));
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                log.error("Error while trying to authenticate user", e);
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token not found");
        }
    }
}
