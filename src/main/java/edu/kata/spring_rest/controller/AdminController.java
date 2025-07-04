package edu.kata.spring_rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping(path = {"", "/"})
    public String getAdminPage() {
        return "admin/admin_panel";
    }
}
