package com.planner.service;

import com.planner.domain.dto.UserDTO;
import com.planner.domain.entity.user.UserEntity;
import com.planner.domain.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IsLoginService {
    @Autowired
    UserRepository userRepository;

    public UserEntity getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println(principal);

        if(principal.equals("anonymousUser")){
            return null;
        }
        UserDTO userDTO = (UserDTO) principal;
        Optional<UserEntity> userOptional = userRepository.findByuId(userDTO.getUId());
        if(!userOptional.isPresent()){ return null; }
        return userOptional.get();
    }
}
