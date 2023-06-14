package com.planner.controller;

import com.planner.domain.entity.user.UserEntity;
import com.planner.service.IsLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class IsLoginController {

    @Autowired
    private IsLoginService isLoginService;

    @GetMapping("/login-info")
    public String getUserInfo(){
        UserEntity userEntity = isLoginService.getUserInfo();
        return userEntity == null ? null : userEntity.getUId();
    }
}
