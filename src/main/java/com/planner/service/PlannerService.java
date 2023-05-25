package com.planner.service;

import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.planner.PlannerRepository;
import com.planner.domain.entity.type.TypeEntity;
import com.planner.domain.entity.type.TypeRepository;
import com.planner.domain.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlannerService {

    @Autowired
    PlannerRepository plannerRepository;
    @Autowired
    TypeRepository typeRepository;

    public boolean userPlannerInit(UserEntity userEntity){

        Optional<TypeEntity> optional = typeRepository.findBytName("personal");

        // 개인 플래너 생성 실패
        if(!optional.isPresent()){ return false; }

        PlannerEntity plannerEntity = PlannerEntity.builder().pOwner(userEntity).type(optional.get()).build();

        plannerRepository.save(plannerEntity);
        return true;
    }
}
