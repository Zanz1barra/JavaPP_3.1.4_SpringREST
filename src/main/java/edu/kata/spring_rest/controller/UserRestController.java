package edu.kata.spring_rest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kata.spring_rest.model.User;
import edu.kata.spring_rest.model.UserDTO;

@RestController
@RequestMapping(path = {"/api/user"})
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserRestController {

    @GetMapping(path = {"/my_data"})
    @ResponseBody
    public UserDTO getSelfUserData(@AuthenticationPrincipal User user) {
        return UserDTO.fromUser(user);
    }

}
