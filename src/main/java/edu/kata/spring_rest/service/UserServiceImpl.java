package edu.kata.spring_rest.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import edu.kata.spring_rest.model.User;
import edu.kata.spring_rest.model.UserDTO;
import edu.kata.spring_rest.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getUsersList() {
        return userRepository
                .findAll().stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return UserDTO.fromUser(
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User with id = " + userId + " not found")));
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        try {
            passwordEncoder.matches("a", user.getPassword());
        } catch (Exception e) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setAge(userDTO.getAge());
        user.setRoles(userDTO.getRoles());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        return UserDTO.fromUser(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(new User());
        user.setUsername(userDTO.getUsername(user.getUsername()));
        user.setPassword(userDTO.getPassword(user.getPassword()));
        try {
            passwordEncoder.matches("a", user.getPassword());
        } catch (Exception e) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setAge(userDTO.getAge(user.getAge()));
        user.setRoles(userDTO.getRoles());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        System.out.println(user);
        return UserDTO.fromUser(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public void deleteUser(UserDTO userDTO) {
        if (userDTO != null) {
            deleteUserById(userDTO.getId());
        }
    }
}
