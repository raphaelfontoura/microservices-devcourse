package com.rddev.hruser.dtos;

import com.rddev.hruser.entities.Role;
import com.rddev.hruser.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserViewDto {

    private Long id;
    private String name;
    private String email;

    private Set<Role> roles;

    public UserViewDto(User user) {
        if (user == null) {
            return;
        }
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
