package com.planner.controller;

import com.planner.domain.dto.TodoDTO;
import com.planner.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoService;

    @PostMapping("/duty")
    public int createTodo(@RequestBody TodoDTO todoDTO){
        return todoService.createTodo(todoDTO);
    }

    @PutMapping("/completed")
    public int completed(@RequestBody TodoDTO todoDTO){
        return todoService.completed(todoDTO);
    }
    @GetMapping("/list/planner")
    public List<TodoDTO> getList(@RequestParam int pno){
        return todoService.getList(pno);
    }
    @GetMapping("/list/day")
    public List<TodoDTO> getDayList(@RequestParam int pno, @RequestParam int year, @RequestParam int month, @RequestParam int day){
        return todoService.getDayList(pno, year, month, day );
    }
    @GetMapping("/duty")
    public TodoDTO getTodo(@RequestParam int tno){
        return todoService.getTodo(tno);
    }

    @PutMapping("/duty")
    public int updateTodo(@RequestBody TodoDTO todoDTO){
        return todoService.updateTodo(todoDTO);
    }
    @DeleteMapping("/duty")
    public int deleteTodo(@RequestBody TodoDTO todoDTO){
        return todoService.deleteTodo(todoDTO);
    }

    @GetMapping("/month-list")
    public List<TodoDTO> getMonthList(@RequestParam int pno, @RequestParam int year, @RequestParam int month){
        return todoService.getMonthList(pno, year, month);
    }
}