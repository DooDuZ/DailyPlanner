package com.planner.domain.dto;

import com.planner.domain.entity.todo.TodoEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eTime;
    private boolean doOrNot;

    // FK
    private Integer opener;
    private Integer closer;
    private Integer pno;

    // 반환용 필드
    private String openerName;
    private String closerName;

    public TodoEntity toEntity(){
        return TodoEntity.builder().tNo(this.tNo).tText(this.tText).tTitle(this.tTitle)
                .sTime(this.sTime).eTime(this.eTime).doOrNot(this.doOrNot).build();
    }
}
