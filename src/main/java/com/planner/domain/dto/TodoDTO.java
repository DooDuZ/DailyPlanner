package com.planner.domain.dto;


import com.planner.domain.entity.todo.TodoEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private int tNo;
    private String tTitle;
    private String tText;

    private Date sTime;
    private Date eTime;
    private boolean doOrNot;

    //FK
    private int opener;
    private int closer;
    private int pno;

    public TodoEntity toEntity(){
        return TodoEntity.builder().tNo(this.tNo).tText(this.tText).tTitle(this.tTitle)
                .sTime(this.sTime).eTime(this.eTime).doOrNot(this.doOrNot).build();
    }
}
