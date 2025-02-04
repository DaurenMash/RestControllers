package ru.kata.spring.boot_security.demo.DTO;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final RoleService roleService;

    public UserConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    public  User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        List<Long> roleIds = new ArrayList<>(userDTO.getRoles());
        Set<Role> roles = roleService.findByIds(roleIds);

        user.setRoles((List<Role>) roles);
        return user;
    }
}
