package com.planner.service;

import com.planner.domain.dto.PlannerDTO;
import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.planner.PlannerRepository;
import com.planner.domain.entity.type.TypeEntity;
import com.planner.domain.entity.type.TypeRepository;
import com.planner.domain.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlannerService {
    @Autowired
    PlannerRepository plannerRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    AuthService authService;
    @Autowired
    IsLoginService isLoginService;

    @Transactional
    public boolean userPlannerInit(UserEntity userEntity){

        Optional<TypeEntity> optional = typeRepository.findBytName("personal");

        // 개인 플래너 생성 실패
        if(!optional.isPresent()){ return false; }

        PlannerEntity plannerEntity = PlannerEntity.builder().pOwner(userEntity).type(optional.get()).build();
        plannerRepository.save(plannerEntity);

        // 권한 생성 및 등록
        authService.createAuth(userEntity, plannerEntity);

        return true;
    }

    public List<PlannerDTO> getList(){
        List<PlannerDTO> plannerList = new ArrayList<>();

        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity==null){ return null;}

        for(AuthEntity authEntity : userEntity.getAuthEntityList()){
            plannerList.add(authEntity.getPlannerEntity().toDTO());
        }

        return plannerList;
    }
}
