package edu.kata.spring_rest.controller;

import edu.kata.spring_rest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import edu.kata.spring_rest.model.UserDTO;
import edu.kata.spring_rest.service.UserService;

@RestController
@RequestMapping(path = {"/api/users"})
public class UsersRestController {
    private final UserService userService;

    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"/user_list"})
    @ResponseBody
    public List<UserDTO> getUserList() {
        return userService.getUsersList();
    }

    @GetMapping(path = {"/my_data"})
    @ResponseBody
    public UserDTO getSelfUserData(@AuthenticationPrincipal User user) {
        return UserDTO.fromUser(user);
    }

    @GetMapping(path = {"/user_data/{id}"})
    @ResponseBody
    public UserDTO getUserData(@PathVariable(name = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(path = {"/add_user"})
    @ResponseBody
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping(path = {"/update_user"})
    @ResponseBody
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping(path = {"/delete_user"})
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        userService.deleteUser(userDTO);
        return ResponseEntity.ok().build();
    }
}
