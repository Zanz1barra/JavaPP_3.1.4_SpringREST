package edu.kata.spring_rest.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.kata.spring_rest.model.User;

@Controller
@RequestMapping(path = {"/user"})
public class UserController {
    @RequestMapping(
            method = RequestMethod.GET,
            path = {"", "/"})
    public String getUserData(
            @AuthenticationPrincipal User user,
            ModelMap modelMap) {
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("isUser", true);
        return "user/user_data";
    }
}
