package com.planner.controller;

import com.planner.domain.dto.UserDTO;
import com.planner.domain.dto.UserInfoDTO;
import com.planner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController{
    @Autowired
    UserService userService;
    @PostMapping("/sign-up")
    public int signUp(@RequestBody UserDTO userDTO){
        return userService.signUp(userDTO);
    }

    @GetMapping("/info")
    public UserInfoDTO getInfo(@RequestParam int uno){
        return userService.getInfo(uno);
    }

    @PostMapping("/check-user")
    public boolean checkUser(@RequestParam String upassword){
        return userService.checkUser(upassword);
    }
}
