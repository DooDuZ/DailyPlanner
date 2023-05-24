package com.planner.domain.entity.planner;

import com.planner.domain.dto.AuthDTO;
import com.planner.domain.dto.PlannerDTO;
import com.planner.domain.entity.auth.AuthEntity;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planner")
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class PlannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pNo;

    @Column(nullable = false)
    private String pType;

    @OneToMany(mappedBy = "plannerEntity" , cascade = CascadeType.ALL)
    @Builder.Default
    List<AuthEntity> authEntityList = new ArrayList<>();

    public PlannerDTO toDTO(){
        List<AuthDTO> authDTOList = new ArrayList<>();
        for(AuthEntity entity : authEntityList){
            authDTOList.add(entity.toDTO());
        }
        return PlannerDTO.builder().pNo(this.pNo).pType(this.pType).authEntityList(authDTOList).build();
    }
}
