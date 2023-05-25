package com.planner.domain.entity.type;

import com.planner.domain.entity.BaseEntity;
import com.planner.domain.entity.planner.PlannerEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tNo;

    @Column(nullable = false)
    private String tName;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    @Builder.Default
    List<PlannerEntity> plannerEntityList = new ArrayList<>();
}
