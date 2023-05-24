package com.planner.domain.dto;

import com.planner.domain.entity.user.UserEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int uNo;
    private String uId;
    private String uPassword;
    private String uName;
    private String uEmail;
    private String uRole;

    //FK
    private List<TodoDTO> openList = new ArrayList<>();
    private List<TodoDTO> closeList = new ArrayList<>();
    private List<AuthDTO> authList = new ArrayList<>();

    public UserEntity toEntity(){
        return UserEntity.builder().uNo(this.uNo).uId(this.uId).uPassword(this.uPassword)
                .uName(this.uName).uEmail(this.uEmail).uRole(this.uRole).build();
    }
}
