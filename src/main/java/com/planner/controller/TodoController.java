package com.planner.controller;

import com.planner.domain.dto.TodoDTO;
import com.planner.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PostMapping("/duty")
    public int createTodo(@DateTimeFormat(pattern = "yyyy-MM-dd") TodoDTO todoDTO){
        return todoService.createTodo(todoDTO);
    }
}
