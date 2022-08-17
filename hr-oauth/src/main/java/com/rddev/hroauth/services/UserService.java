package com.rddev.hroauth.services;

import com.rddev.hroauth.entities.User;
import com.rddev.hroauth.feignclients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        User user = userFeignClient.findByEmail(email).getBody();
        if (user == null) {
            logger.error("Email not found: {}", email);
            throw new IllegalArgumentException("User not found");
        }
        logger.info("Email found: {}", user.getEmail());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userFeignClient.findByEmail(username).getBody();
        if (user == null) {
            logger.error("User not found: {}", user);
            throw new IllegalArgumentException("User not found");
        }
        logger.info("Email found: {}", user.getUsername());
        return user;
    }
}
