package com.rddev.hruser.resources;

import com.rddev.hruser.dtos.UserViewDto;
import com.rddev.hruser.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserResource {

    private final UserService service;

    @RequestMapping("/{id}")
    public ResponseEntity<UserViewDto> findById(@PathVariable Long id) {
        UserViewDto dto = new UserViewDto(service.findById(id));
        return ResponseEntity.ok(dto);
    }

    @RequestMapping("/search")
    public ResponseEntity<UserViewDto> findByEmail(@RequestParam String email) {
        UserViewDto dto = new UserViewDto(service.findByEmail(email));
        return ResponseEntity.ok(dto);
    }
}
