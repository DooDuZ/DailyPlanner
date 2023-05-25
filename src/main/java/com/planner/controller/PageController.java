package com.planner.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    페이지 전환 시 404 피하기위한 test class
 */

@RestController
@RequestMapping("/")
public class PageController {

    @RequestMapping("/")
    public String getIndex(){
        return "Login Success";
    }
}
