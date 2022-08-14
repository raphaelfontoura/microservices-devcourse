package com.rddev.hroauth.resources;

import com.rddev.hroauth.entities.User;
import com.rddev.hroauth.services.UserService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
