package com.planner.controller;

import com.planner.domain.dto.TodoDTO;
import com.planner.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoControllerTest {

    @Test
    public void createTodo(){
        String sTime = "2023-05-26T18:28";
        LocalDateTime date = LocalDateTime.parse(sTime);
        System.out.println(date);
    }

    @Test
    void completeTodo() {
    }
}