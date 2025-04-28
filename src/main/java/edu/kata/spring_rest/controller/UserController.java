package edu.kata.spring_rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = {"/user"})
public class UserController {

    @GetMapping(path = {"", "/"})
    public String getUserData() {
        return "user/user_data";
    }
}
