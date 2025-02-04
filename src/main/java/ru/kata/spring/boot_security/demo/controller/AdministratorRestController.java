package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;




@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')") // Доступ только для пользователей с ролью ADMIN
public class AdministratorRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdministratorRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Получить всех пользователей
    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Получить пользователя по ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Добавить нового пользователя
    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    // Обновить пользователя
    @PutMapping("/user/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok().build();
    }

    // Удалить пользователя
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    // Получить все роли
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    // Получить данные текущего пользователя
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
//@RestController
//@RequestMapping("/api")
//
//public class AdministratorRestController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//
//    public AdministratorRestController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping("/admin")
//    public ResponseEntity<List<User>> getAll() {
//        List<User> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    @GetMapping("/admin/{id}")
//    public ResponseEntity<User> getById(@PathVariable Long id) {
//        User user = userService.findUserById(id);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
//        userService.save(userDTO);
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
//        userService.delete(id);
//        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
//    }
//
//
//    @PutMapping("/user/{id}")
//    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
//        userService.update(id, userDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/roles")
//    public ResponseEntity<List<Role>> getAllRoles() {
//        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
//    }
//
//    @GetMapping("/current")
//    public ResponseEntity<User> getCurrent(Principal principal) {
//        User user = (User) userService.loadUserByUsername(principal.getName());
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }
//}
//


