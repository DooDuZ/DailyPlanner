package com.planner.domain.dto;


import com.planner.domain.entity.planner.PlannerEntity;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlannerDTO {
    private int pNo;
    private int pType;
    // FK
    List<AuthDTO> authEntityList;
    public PlannerEntity toEntity(){
        return PlannerEntity.builder().pNo(this.pNo).build();
    }
}
