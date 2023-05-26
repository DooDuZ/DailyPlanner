package com.planner.service;

import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.auth.AuthRepository;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Autowired
    AuthRepository authRepository;

    public boolean createAuth(UserEntity userEntity, PlannerEntity plannerEntity){
        try{
            // 접근 권한 생성
            AuthEntity authEntity = AuthEntity.builder().plannerEntity(plannerEntity).userEntity(userEntity).build();
            authRepository.save(authEntity);
            // 권한 등록
            userEntity.getAuthEntityList().add(authEntity);
            plannerEntity.getAuthEntityList().add(authEntity);
        }catch (Exception e){
            log.info("Error creating auth {}", e);
            return false;
        }
        return true;
    }
}
