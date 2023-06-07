package com.planner.controller;

import com.planner.domain.dto.PlannerDTO;
import com.planner.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/planner")
public class PlannerController {

    @Autowired
    PlannerService plannerService;

    @GetMapping("/list")
    public List<PlannerDTO> getList(){
        return plannerService.getList();
    }
}
