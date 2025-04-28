package edu.kata.spring_rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import edu.kata.spring_rest.model.Role;
import edu.kata.spring_rest.service.RoleService;

@RestController
@RequestMapping(path = {"/api/roles"})
public class RolesRestController {
    private final RoleService roleService;

    public RolesRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = {"", "/"})
    public List<Role> getRoles() {
        return roleService.getRolesList();
    }
}
