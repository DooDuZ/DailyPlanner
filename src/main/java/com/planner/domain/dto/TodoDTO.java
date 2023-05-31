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
    private String title;
    private String text;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime sTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eTime;
    private boolean isCompleted;

    // FK
    private int opener;
    private int closer;
    private int pno;

    // 반환용 필드
    private String openerName;
    private String closerName;

    // status 반환 필드
    private int status;

    public TodoEntity toEntity(){
        return TodoEntity.builder().tNo(this.tNo).text(this.text).title(this.title)
                .sTime(this.sTime).eTime(this.eTime).isCompleted(this.isCompleted).build();
    }
}
