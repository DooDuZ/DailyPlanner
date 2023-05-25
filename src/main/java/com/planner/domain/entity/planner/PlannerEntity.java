package com.planner.domain.entity.planner;

import com.planner.domain.dto.AuthDTO;
import com.planner.domain.dto.PlannerDTO;
import com.planner.domain.entity.BaseEntity;
import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.type.TypeEntity;
import com.planner.domain.entity.user.UserEntity;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planner")
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class PlannerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pNo;

    @ManyToOne
    @JoinColumn(name = "uNo")
    @ToString.Exclude
    private UserEntity pOwner;

    @ManyToOne
    @JoinColumn(name = "tNo")
    @ToString.Exclude
    private TypeEntity type;

    @OneToMany(mappedBy = "plannerEntity" , cascade = CascadeType.ALL)
    @Builder.Default
    List<AuthEntity> authEntityList = new ArrayList<>();

    public PlannerDTO toDTO(){
        List<AuthDTO> authDTOList = new ArrayList<>();
        for(AuthEntity entity : authEntityList){
            authDTOList.add(entity.toDTO());
        }
        return PlannerDTO.builder().pNo(this.pNo).authEntityList(authDTOList).build();
    }
}
