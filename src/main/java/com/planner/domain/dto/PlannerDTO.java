package com.planner.domain.dto;


import com.planner.domain.entity.planner.PlannerEntity;
import lombok.*;
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
    private String pName;
    // FK
    List<String> authUserList;
    public PlannerEntity toEntity(){
        return PlannerEntity.builder().pNo(this.pNo).pName(this.pName).build();
    }
}
