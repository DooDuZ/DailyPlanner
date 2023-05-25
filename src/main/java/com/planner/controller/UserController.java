package com.planner.controller;

import com.planner.domain.dto.UserDTO;
import com.planner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController{
    @Autowired
    UserService userService;
    @PostMapping("/sign-up")
    public int signUp(@RequestBody UserDTO userDTO){
        return userService.signUp(userDTO);
    }
}
