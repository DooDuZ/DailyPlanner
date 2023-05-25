package com.planner.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;

/*
    페이지 전환 시 404 피하기위한 test class
 */

@RestController
@RequestMapping("/")
public class PageController {

    @RequestMapping("/")
    public Resource getIndex(){
        return new ClassPathResource("templates/index.html");
    }
    @RequestMapping("/login")
    public Resource getLogin(){
        return new ClassPathResource("templates/login.html");
    }
}
