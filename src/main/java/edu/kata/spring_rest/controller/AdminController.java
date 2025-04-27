package edu.kata.spring_rest.controller;

import edu.kata.spring_rest.model.UserDTO;
import edu.kata.spring_rest.service.RoleService;
import edu.kata.spring_rest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = {"/admin"})
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {"", "/"})
    public String getAdminPage(ModelMap modelMap) {
        modelMap.addAttribute("isAdmin", true);
        modelMap.addAttribute("allRoles", roleService.getRolesList());
        return "admin/admin_panel";
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {"/user_list"}
    )
    @ResponseBody
    public List<UserDTO> getUserList() {
        return userService.getUsersList();
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {"/user_data/{id}"})
    @ResponseBody
    public UserDTO getUserData(@PathVariable(name = "id") Long userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            path = {"/update"})
    @ResponseBody
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }
}
