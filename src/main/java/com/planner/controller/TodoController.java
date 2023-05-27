package com.planner.controller;

import com.planner.domain.dto.TodoDTO;
import com.planner.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoService;

    @PostMapping("/duty")
    public int createTodo(TodoDTO todoDTO){
        return todoService.createTodo(todoDTO);
    }

    @PutMapping("/completed")
    public int completed(@RequestBody TodoDTO todoDTO){
        System.out.println("요기"+todoDTO);
        return todoService.completed(todoDTO);
    }

    @GetMapping("/list")
    public List<TodoDTO> getPersonalList(){
        return todoService.getPersonalList();
    }

    @PutMapping("/duty")
    public int updateTodo(@RequestBody TodoDTO todoDTO){
        return todoService.updateTodo(todoDTO);
    }
}