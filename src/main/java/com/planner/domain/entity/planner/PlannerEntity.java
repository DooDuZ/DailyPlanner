package com.planner.domain.entity.planner;

import com.planner.domain.dto.PlannerDTO;
import com.planner.domain.entity.BaseEntity;
import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.todo.TodoEntity;
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

    private String pName;

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
    @ToString.Exclude
    List<TodoEntity> todoEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "plannerEntity" , cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    List<AuthEntity> authEntityList = new ArrayList<>();

    public PlannerDTO toDTO(){
        List<String> authUserList = new ArrayList<>();
        for(AuthEntity entity : authEntityList){
            UserEntity userEntity = entity.getUserEntity();
            authUserList.add(userEntity.getUId());
        }
        return PlannerDTO.builder().pNo(this.pNo).authUserList(authUserList).pName(this.pName).pType(type.getTNo()).build();
    }
}
