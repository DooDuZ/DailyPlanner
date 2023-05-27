package com.planner.service;

import com.planner.domain.dto.TodoDTO;
import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.planner.PlannerRepository;
import com.planner.domain.entity.todo.TodoEntity;
import com.planner.domain.entity.todo.TodoRepository;
import com.planner.domain.entity.user.UserEntity;
import com.planner.domain.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    UserRepository userRepository;

    @Transactional
    public int createTodo(TodoDTO todoDTO){
        log.info("createTodo param {}", todoDTO);
        UserEntity userEntity = userService.getUserInfo();

        if(userEntity == null){ return 0; }// 로그인 정보 없음

        TodoEntity todoEntity = todoDTO.toEntity();
        todoEntity.setOpener(userEntity);

        Optional<PlannerEntity> optional = plannerRepository.findById(todoDTO.getPno());
        if(!optional.isPresent()){ return 2;} // 플래너 정보 없음

        PlannerEntity plannerEntity = optional.get();

        if(!checkAuth(userEntity, plannerEntity)){ return 3; } // 권한 없음

        todoEntity.setPlannerEntity(plannerEntity);

        todoRepository.save(todoEntity);

        return 1;
    }

    // 접근 권한 체크
    // 순회 데이터의 크기가 커질 경우 sql query 작성 필요
    public boolean checkAuth(UserEntity userEntity, PlannerEntity plannerEntity){
        boolean ret = false;
        for(AuthEntity authEntity : userEntity.getAuthEntityList()){
            if(authEntity.getPlannerEntity()==plannerEntity){
                ret = true;
                break;
            }
        }
        return ret;
    }

    @Transactional
    public int completed(TodoDTO todoDTO){
        log.info("Completed", todoDTO);
        UserEntity userEntity = userService.getUserInfo();
        if(userEntity == null){ return 0; } // 로그인 정보 없음

        Optional<TodoEntity> todoOptional = todoRepository.findById(todoDTO.getTNo());
        if(!todoOptional.isPresent()){ return 2; } // 게시물 정보 없음

        TodoEntity todoEntity = todoOptional.get();
        PlannerEntity plannerEntity = todoEntity.getPlannerEntity();

        if(!checkAuth(userEntity, plannerEntity)){ return 3;} // 권한 없음

        // 작업 완료
        todoEntity.setCompleted(todoDTO.isCompleted());

        // 완료 대상 변경 -> 기존 완료 유저의 완료 리스트에서 작업 삭제
        if(todoEntity.getCloser()!=null){
            todoEntity.getCloser().getCloseList().remove(todoEntity);
            // 완료자 제거
            todoEntity.setCloser(null);
        }

        // 작업 완료 처리한 유저 등록
        if(todoDTO.isCompleted()){
            if(todoEntity.getCloser() == null){
                todoEntity.setCloser(userEntity);
                // 종료한 작업 매핑
                userEntity.getCloseList().add(todoEntity);
            }
        }

        return 1;
    }

    public List<TodoDTO> getPersonalList(){
        List<TodoDTO> todoList = new ArrayList<>();
        UserEntity userEntity = userService.getUserInfo();
        if(userEntity == null){ return null; } // 유저 정보 없음

        PlannerEntity plannerEntity = null;

        // 권한을 가진 플래너 순회, 개인 플래너 번호 찾기
            // 이것도 쿼리문으로 처리하는 게 나을까?
        for(AuthEntity authEntity : userEntity.getAuthEntityList()){
            PlannerEntity p = authEntity.getPlannerEntity();
            if(p.getType().getTName().equals("personal")){
                plannerEntity = p;
                log.info("getPersonalList - planner {}", p);
                break;
            }
        }

        System.out.println(userEntity);

        if(plannerEntity!=null){
            for(TodoEntity todoEntity : plannerEntity.getTodoEntityList()){
                todoList.add(todoEntity.toDTO());
            }
        }

        log.info("getPersonalList {}", todoList);

        return todoList;
    }

    // 테스트 필요함!!
    @Transactional
    public int updateTodo(TodoDTO todoDTO){
        log.info("updateTodo", todoDTO);

        if(!checkData(todoDTO)){ return -1; }

        UserEntity userEntity = userService.getUserInfo();
        if(userEntity == null){ return 0; } // 유저 정보 없음
        Optional<PlannerEntity> plannerOptional = plannerRepository.findById(todoDTO.getPno());
        if(!plannerOptional.isPresent()){ return 2; } // 플래너 정보 없음
        PlannerEntity plannerEntity = plannerOptional.get();
        if(!checkAuth(userEntity, plannerEntity)){ return 3; } // 권한 없음

        Optional<TodoEntity> todoOptional = todoRepository.findById( todoDTO.getTNo() );
        if(!todoOptional.isPresent()){ return 4; } // 존재하지 않는 기록 ex)작업 중 누군가 삭제한 경우

        TodoEntity todoEntity = todoOptional.get();
/*
        complete 매서드 통해서 처리되는 내용이다.
        // 작업 완료 체크
        if(todoEntity.getCloser()!=null){
            Optional<UserEntity> closerOptional = userRepository.findById(todoDTO.getCloser());
            if(!closerOptional.isPresent()){ return 5; } // 잘못된 완료 작업자 정보
            // 완료된 작업이면 기존 작업자의 종료 기록에서 삭제
            todoEntity.getCloser().getCloseList().remove(todoEntity);
            todoEntity.setCloser(closerOptional.get()); // 완료자 변경
        }
*/
        // 내용 변경 처리
        todoEntity.setTTitle(todoDTO.getTTitle());      // 제목
        todoEntity.setTText(todoDTO.getTText());        // 내용
        todoEntity.setSTime(todoDTO.getSTime());        // 시작 시간
        todoEntity.setETime(todoDTO.getETime());        // 종료 시간
        // todoEntity.setCompleted(todoDTO.isCompleted()); // 완료 여부

        completed(todoDTO);

        return 1;
    }

    public boolean checkData(TodoDTO todoDTO){
        if(todoDTO.getTNo() != 0 || todoDTO.getPno() != 0 || todoDTO.getTTitle() != null ){
            return true;
        }
        return false;
    }
}
