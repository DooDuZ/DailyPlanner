package com.planner.domain.entity.todo;

import com.planner.domain.dto.TodoDTO;
import com.planner.domain.entity.BaseEntity;
import com.planner.domain.entity.planner.PlannerEntity;
import com.planner.domain.entity.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tNo;

    @Column(nullable = false)
    private String tTitle;

    @Column(columnDefinition = "LONGTEXT")
    private String tText;

    private LocalDateTime sTime;
    private LocalDateTime eTime;

    @ColumnDefault("false")
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "open_uNo")
    @Column( updatable = false)
    @ToString.Exclude
    private UserEntity opener;

    @ManyToOne
    @JoinColumn(name = "close_uNo")
    @ToString.Exclude
    private UserEntity closer;

    @ManyToOne
    @JoinColumn(name = "pNo")
    @Column( updatable = false)
    @ToString.Exclude
    private PlannerEntity plannerEntity;

    public TodoDTO toDTO(){
        return TodoDTO.builder().tNo(this.tNo).tText(this.tText)
                .tTitle(this.tTitle).sTime(this.sTime).eTime(this.eTime)
                .isCompleted(this.isCompleted).opener(this.opener.getUNo()).closer( (this.closer == null) ? 0 : this.closer.getUNo() )
                .pno(this.plannerEntity.getPNo()).openerName(this.opener.getUId()).closerName(this.closer== null ? "" : this.closer.getUId()).build();
    }
}
