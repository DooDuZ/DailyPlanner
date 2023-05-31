package com.planner.service;

import com.planner.domain.dto.UserDTO;
import com.planner.domain.dto.UserInfoDTO;
import com.planner.domain.entity.user.UserEntity;
import com.planner.domain.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlannerService plannerService;
    @Autowired
    IsLoginService isLoginService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByuId(username)
                .orElseThrow( () -> new UsernameNotFoundException("존재하지 않는 사용자") );

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getURole()));

        log.info("loadUserByUsername Entity{}", userEntity);
        UserDTO userDTO = userEntity.toDTO();
        log.info("loadUserByUsername DTO{}", userDTO);
        userDTO.setAuthorities(authorities);

        return userDTO;
    }

    @Transactional
    public int signUp(UserDTO userDTO){
        // 유저 정보 미기입
        if(!checkEmpty(userDTO.getUId()) || !checkEmpty(userDTO.getUPassword()) || !checkEmpty(userDTO.getUEmail()) || !checkEmpty(userDTO.getUName())){
            log.info("Sign Up INFO {}", userDTO);
            return 0;
        }
        // 아이디 중복 검사
        if(userRepository.findByuId(userDTO.getUId()).isPresent()) {
            log.info("Sign Up ID INFO {}", userDTO.getUId());
            return 2;
        }
        // 이메일 중복 검사
        if(userRepository.findByuEmail(userDTO.getUEmail()).isPresent()){
            log.info("Sign Up Email INFO {}", userDTO.getUEmail());
            return 3;
        }
        userDTO.setUPassword(passwordEncoder.encode(userDTO.getUPassword()));
        userDTO.setURole("일반회원");

        UserEntity userEntity = userDTO.toEntity();

        // 개인 플래너 생성 실패
        if(!plannerService.userPlannerInit(userEntity)){
            log.info("User Init INFO {}");
            return 4;
        }

        if(userRepository.save(userEntity).getUNo()!=0){
            return 1;
        };
        return 5; // 내부 오류
    }

    // 문자열 데이터 검증 -> 추후 유효성검사 추가
    public boolean checkEmpty(String str){
        if(str==null || str.equals("")){
            return false;
        }
        return true;
    }

    public UserInfoDTO getInfo(int uno){
        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity == null ){ return null;}  // 본인 정보 아님
        return userEntity.toUserInfoDTO();
    }

    public boolean checkUser(String upassword){
        UserEntity userEntity = isLoginService.getUserInfo();
        if(userEntity == null){ return false; }
        if(passwordEncoder.matches(upassword, userEntity.getUPassword())){
            return true;
        }
        return false;
    }
}
