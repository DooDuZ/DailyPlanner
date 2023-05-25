package com.planner.service;

import com.planner.domain.dto.TodoDTO;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.planner.PlannerRepository;
import com.planner.domain.entity.todo.TodoEntity;
import com.planner.domain.entity.todo.TodoRepository;
import com.planner.domain.entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class TodoService {

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    PlannerRepository plannerRepository;
    @Autowired
    UserService userService;

    @Transactional
    public int createTodo(TodoDTO todoDTO){
        UserEntity userEntity = userService.getUserInfo();
        if(userEntity == null){ return 0; } // 로그인 정보 없음
        log.info("createTodo DTO {}", todoDTO);
        log.info("createTodo user {}", userEntity);
        TodoEntity todoEntity = todoDTO.toEntity();
        todoEntity.setOpener(userEntity);

        Optional<PlannerEntity> optional = plannerRepository.findById(todoDTO.getPno());
        if(!optional.isPresent()){ return 2;} // 플래너 정보 없음
        todoEntity.setPlannerEntity(optional.get());

        todoRepository.save(todoEntity);

        return 1;
    }
}
