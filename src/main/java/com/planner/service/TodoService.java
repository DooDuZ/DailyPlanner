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
import java.time.LocalDateTime;
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
    IsLoginService isLoginService;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public int createTodo(TodoDTO todoDTO){
        if(todoDTO.getTitle() == null || todoDTO.getTitle().equals("") || todoDTO.getPno() == 0){
            log.info("createTodo {}", todoDTO );
            return -1; // 입력 정보 오류
        }

        log.info("createTodo param {}", todoDTO);
        UserEntity userEntity = isLoginService.getUserInfo();

        if(userEntity == null){ return 0; } // 로그인 정보 없음

        TodoEntity todoEntity = todoDTO.toEntity();
        todoEntity.setOpener(userEntity);

        Optional<PlannerEntity> optional = plannerRepository.findById(todoDTO.getPno());
        if(!optional.isPresent()){ return 2;} // 플래너 정보 없음

        PlannerEntity plannerEntity = optional.get();

        if(!checkAuth(userEntity, plannerEntity)){ return 3; } // 권한 없음

        todoEntity.setPlannerEntity(plannerEntity);

        if(todoRepository.save(todoEntity).getTNo() != 0 ){ // 저장 성공
            return 1;
        }

        return 5; // 데이터 저장 실패
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
        UserEntity userEntity = isLoginService.getUserInfo();
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
            todoEntity.setCloser(userEntity);
            if (todoEntity.getETime()==null){
                todoEntity.setETime(LocalDateTime.now());
            }
            // 종료한 작업 매핑
            userEntity.getCloseList().add(todoEntity);
        }

        return 1;
    }

    public List<TodoDTO> getList(int pno){
        List<TodoDTO> todoList = new ArrayList<>();
        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity == null){ return null; } // 유저 정보 없음

        PlannerEntity plannerEntity = null;

        // 권한을 가진 플래너 순회, 개인 플래너 번호 찾기
            // 이것도 쿼리문으로 처리하는 게 나을까?
        for(AuthEntity authEntity : userEntity.getAuthEntityList()){
            PlannerEntity p = authEntity.getPlannerEntity();
            if(p.getPNo() == pno){
                plannerEntity = p;
                log.info("getList - planner {}", p);
                break;
            }
        }

        if(plannerEntity!=null){
            for(TodoEntity todoEntity : plannerEntity.getTodoEntityList()){
                todoList.add(todoEntity.toDTO());
            }
        }

        return todoList;
    }

    public List<TodoDTO> getMonthList(int pno, int year, int month){
        List<TodoDTO> todoList = new ArrayList<>();
        UserEntity userEntity = isLoginService.getUserInfo();

        if(userEntity == null){ // 유저 정보 없음
            log.info("getMonthList {}", "empty user");
            return null;
        }

        PlannerEntity plannerEntity = null;

        for(AuthEntity authEntity : userEntity.getAuthEntityList()){
            PlannerEntity p = authEntity.getPlannerEntity();
            if(p.getPNo() == pno){
                plannerEntity = p;
                log.info("getMonthList - planner {}", p);
                break;
            }
        }

        if(plannerEntity==null){ return null;}  // 플래너 권한 없음

        String date = String.valueOf(year) + "-" + ( month < 10 ? "0"+String.valueOf(month) : String.valueOf(month) );

        List<TodoEntity> entityList = todoRepository.findByMonth(pno, date);

        for(TodoEntity entity : entityList){
            todoList.add(entity.toDTO());
        }

        return todoList;
    }

    @Transactional
    public int updateTodo(TodoDTO todoDTO){
        log.info("updateTodo {}", todoDTO);

        if(!checkData(todoDTO)){ return -1; }

        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity == null){ return 0; } // 유저 정보 없음
        Optional<TodoEntity> todoOptional = todoRepository.findById( todoDTO.getTNo() );
        if(!todoOptional.isPresent()){ return 2; } // 존재하지 않는 기록 ex)작업 중 누군가 삭제한 경우

        TodoEntity todoEntity = todoOptional.get();

        PlannerEntity plannerEntity;
        try{
            plannerEntity = todoEntity.getPlannerEntity();
        }catch (Exception e){
            log.error("UPDATE ERROR!! {}", e);
            return 4;
        }

        if(!checkAuth(userEntity, plannerEntity)){ return 4; } // 권한 없음

        // 내용 변경 처리
        if(todoDTO.getTitle() != null ){
            todoEntity.setTitle(todoDTO.getTitle());  // 제목
        }
        todoEntity.setText(todoDTO.getText());        // 내용
        if(todoDTO.getSTime() != null){
            todoEntity.setSTime(todoDTO.getSTime());    // 시작 시간
        }
        todoEntity.setETime(todoDTO.getETime());        // 종료 시간

        /*
        if(todoDTO.getETime()==null){ // 종료 시간 미입력 시
            todoEntity.setCloser(null); // 작업 완료자 삭제
        }else{
            if(todoDTO.getCloser() == 0){   // 종료시간 존재 + 작업 완료자 없는 경우
                todoEntity.setCloser(userEntity);   // 수정한 유저를 완료자로 등록
            }else{
                Optional<UserEntity> closerOptional = userRepository.findById(todoDTO.getCloser());
                if(closerOptional.isPresent()){     // 종료 입력된 유저가 존재하면 세팅
                    todoEntity.setCloser(closerOptional.get());
                }else{
                    todoEntity.setCloser(userEntity);   // 없는 경우 수정한 유저로 등록
                }
            }
        }
        */

        // 완료 처리
        if(completed(todoDTO)==1){ return 1; }

        return 5; // 완료 여부 변경 실패
    }

    public boolean checkData(TodoDTO todoDTO){
        if(todoDTO.getTNo() != 0 || todoDTO.getTitle() != null ){
            return true;
        }
        return false;
    }

    public int deleteTodo(TodoDTO todoDTO){
        log.info("deleteTodo", todoDTO);
        if(todoDTO.getTNo()==0){ return -1; } // 삭제할 게시물 번호 없음
        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity == null){ return 0; } // 유저 정보 없음

        Optional<TodoEntity> todoOptional = todoRepository.findById(todoDTO.getTNo());
        if(!todoOptional.isPresent()){ return 2; }  // 존재하지 않는 게시물

        TodoEntity todoEntity = todoOptional.get();
        PlannerEntity plannerEntity;

        // 게시물 번호만 받기 때문에 planner 정보를 확인하는데 안전하지 않음
            // ex) 삭제 작업 진행 중 누군가 해당 플래너 자체를 삭제하는 경우
            // 영속성 전이를 통해 게시물 존재 여부에서 필터링 된다면 다행이지만 혹시나 그렇지 않은 경우를 위해 try/catch
        try{
            plannerEntity = todoEntity.getPlannerEntity();
        }catch (Exception e){
            log.error("deleteTodo {}", e);
            return 3;   // 플래너 정보 없음
        }

        if(!checkAuth(userEntity, plannerEntity)){ return 4; } // 권한 없음

        // 삭제 처리
        todoRepository.delete(todoEntity);

        return 1;
    }

    public TodoDTO getTodo(int tno){
        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity == null){
            return TodoDTO.builder().status(0).build();
        } // 유저 정보 없음
        Optional<TodoEntity> todoOptional = todoRepository.findById(tno);
        if(!todoOptional.isPresent()){
            return TodoDTO.builder().status(2).build();
        }
        TodoEntity todoEntity = todoOptional.get();

        if(!checkAuth(userEntity, todoEntity.getPlannerEntity())){
            return TodoDTO.builder().status(3).build(); // 권한 없는 게시물
        }

        TodoDTO todoDTO = todoEntity.toDTO(); // status default 1

        return todoDTO;
    }

    public List<TodoDTO> getDayList(int pno, int year, int month, int day){
        List<TodoDTO> todoList = new ArrayList<>();
        UserEntity userEntity = isLoginService.getUserInfo();

        if(userEntity == null){ // 유저 정보 없음
            log.info("getDayList {}", "empty user");
            return null;
        }

        PlannerEntity plannerEntity = null;

        for(AuthEntity authEntity : userEntity.getAuthEntityList()){
            PlannerEntity p = authEntity.getPlannerEntity();
            if(p.getPNo() == pno){
                plannerEntity = p;
                log.info("getDayList - planner {}", p);
                break;
            }
        }

        if(plannerEntity==null){ return null;}  // 플래너 권한 없음

        String date = String.valueOf(year) + "-" + ( month < 10 ? "0"+String.valueOf(month) : String.valueOf(month) )+ "-" + ( day < 10 ? "0"+String.valueOf(day) : String.valueOf(day) );

        List<TodoEntity> entityList = todoRepository.findByMonth(pno, date);

        for(TodoEntity entity : entityList){
            todoList.add(entity.toDTO());
        }

        return todoList;
    }
}
