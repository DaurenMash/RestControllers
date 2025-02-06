package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.Mapper;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;
    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoderConfig passwordEncoder,
                           Mapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    @Transactional
    public void update(User updateUser) {
        User user = findUserById(updateUser.getId());
        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.passwordEncoder().encode(updateUser.getPassword()));
        }
        user.setRoles(updateUser.getRoles());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, UserDTO userDTO) {
        User userToUpdate = mapper.toUser(userDTO);
        userToUpdate.setId(id);
        update(userToUpdate);
    }


    @Override
    @Transactional
    public void delete(long id) {
        findUserById(id);
        userRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User с данным id не найден"));
    }


    @Override
    @Transactional
    public void save(UserDTO userDTO){
        User user = mapper.toUser(userDTO);
        user.setPassword(passwordEncoder.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true)
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User с именем '%s' не найден", username));
        }
        return user;
    }


}