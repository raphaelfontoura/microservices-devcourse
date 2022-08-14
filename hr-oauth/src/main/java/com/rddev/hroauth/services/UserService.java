package com.rddev.hroauth.services;

import com.rddev.hroauth.entities.User;
import com.rddev.hroauth.feignclients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        User user = userFeignClient.findByEmail(email).getBody();
        if (user == null) {
            logger.error("Email not found: {}", email);
            throw new IllegalArgumentException("User not found");
        }
        logger.info("Email found: {}", user.email());
        return user;
    }
}
