package com.planner.domain.entity.todo;

import com.planner.domain.dto.TodoDTO;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todo")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tNo;

    @Column(nullable = false)
    private String tTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String tText;

    private Date sTime;
    private Date eTime;

    @ColumnDefault("false")
    private boolean doOrNot;

    @ManyToOne
    @JoinColumn(name = "open_uNo")
    @ToString.Exclude
    private UserEntity opener;

    @ManyToOne
    @JoinColumn(name = "close_uNo")
    @ToString.Exclude
    private UserEntity closer;

    @ManyToOne
    @JoinColumn(name = "pNo")
    @ToString.Exclude
    private PlannerEntity plannerEntity;

    public TodoDTO toDTO(){
        return TodoDTO.builder().tNo(this.tNo).tText(this.tText)
                .tTitle(this.tTitle).sTime(this.sTime).eTime(this.eTime)
                .doOrNot(this.doOrNot).opener(this.opener.getUNo()).closer(this.closer.getUNo())
                .pno(this.plannerEntity.getPNo()).build();
    }
}
