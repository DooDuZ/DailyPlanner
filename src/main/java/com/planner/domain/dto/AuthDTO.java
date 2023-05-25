package com.planner.domain.dto;

import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.user.UserEntity;
import lombok.*;
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    private int aNo;
    // FK
    private int uNo;
    private int pno;

    public AuthEntity toEntity(){
        return AuthEntity.builder().aNo(this.aNo).build();
    }
}
