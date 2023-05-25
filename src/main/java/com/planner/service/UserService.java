package com.planner.service;

import com.planner.domain.dto.UserDTO;
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
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByuId(username)
                .orElseThrow( () -> new UsernameNotFoundException("존재하지 않는 사용자") );

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userEntity.getURole()));

        UserDTO userDTO = userEntity.toDTO();
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
        userRepository.save(userDTO.toEntity());
        return 1;
    }

    public boolean checkEmpty(String str){
        if(str==null || str.equals("")){
            return false;
        }
        return true;
    }
}
