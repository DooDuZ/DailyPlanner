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
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    private LocalDateTime sTime;
    private LocalDateTime eTime;

    @ColumnDefault("false")
    private boolean isCompleted;

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
        return TodoDTO.builder().tNo(this.tNo).text(this.text)
                .title(this.title).sTime(this.sTime).eTime(this.eTime)
                .completed(this.isCompleted).opener(this.opener.getUNo()).closer( (this.closer == null) ? 0 : this.closer.getUNo() )
                .pno(this.plannerEntity.getPNo()).openerName(this.opener.getUId()).status(1)
                .closerName(this.closer== null ? "" : this.closer.getUId()).build();
    }
}
