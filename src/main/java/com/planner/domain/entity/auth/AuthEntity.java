package com.planner.domain.entity.auth;

import com.planner.domain.dto.AuthDTO;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.user.UserEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "auth")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aNo;

    @ManyToOne
    @JoinColumn(name = "uNo")
    @ToString.Exclude
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "pNo")
    @ToString.Exclude
    private PlannerEntity plannerEntity;

    public AuthDTO toDTO(){
        return AuthDTO.builder().aNo(this.aNo).pno(this.plannerEntity.getPNo()).uNo(this.userEntity.getUNo()).build();
    }
}
