package edu.kata.spring_rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import edu.kata.spring_rest.model.Role;
import edu.kata.spring_rest.model.UserDTO;
import edu.kata.spring_rest.service.RoleService;
import edu.kata.spring_rest.service.UserService;

@RestController
@RequestMapping(path = {"/api/admin"})
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestController {
    private final RoleService roleService;
    private final UserService userService;

    public AdminRestController(RoleService roleService,
                               UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(path = { "/role_list"})
    public List<Role> getRoleList() {
        return roleService.getRolesList();
    }

    @GetMapping(path = {"/user_list"})
    public List<UserDTO> getUserList() {
        return userService.getUsersList();
    }

    @GetMapping(path = {"/user_data/{id}"})
    public UserDTO getUserData(@PathVariable(name = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(path = {"/add_user"})
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping(path = {"/update_user"})
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @DeleteMapping(path = {"/delete_user"})
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        userService.deleteUser(userDTO);
        return ResponseEntity.ok().build();
    }
}
