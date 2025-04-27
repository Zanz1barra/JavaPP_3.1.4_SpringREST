package edu.kata.spring_rest.controller;

import edu.kata.spring_rest.model.UserDTO;
import edu.kata.spring_rest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = {"/admin"})
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = {"", "/"})
    public String getAdminPage(ModelMap modelMap) {
        modelMap.addAttribute("isAdmin", true);
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
}
