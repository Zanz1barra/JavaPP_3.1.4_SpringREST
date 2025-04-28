package edu.kata.spring_rest.service;

import java.util.List;

import edu.kata.spring_rest.model.UserDTO;

public interface UserService {
    List<UserDTO> getUsersList();
    UserDTO getUserById(Long userId);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUserById(Long userId);

    void deleteUser(UserDTO userDTO);
}
