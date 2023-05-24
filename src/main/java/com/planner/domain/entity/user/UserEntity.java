package com.planner.domain.entity.user;

import com.planner.domain.dto.AuthDTO;
import com.planner.domain.dto.TodoDTO;
import com.planner.domain.dto.UserDTO;
import com.planner.domain.entity.auth.AuthEntity;
import com.planner.domain.entity.todo.TodoEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uNo;
    @Column(nullable = false, unique = true)
    private String uId;
    @Column(nullable = false)
    private String uPassword;
    @Column(nullable = false)
    private String uName;
    @Column(nullable = false)
    private String uEmail;
    @Column(nullable = false)
    private String uRole;

    @OneToMany(mappedBy = "opener" , cascade = CascadeType.ALL)
    @Builder.Default
    List<TodoEntity> openList = new ArrayList<>();

    @OneToMany(mappedBy = "closer" , cascade = CascadeType.ALL)
    @Builder.Default
    List<TodoEntity> closeList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @Builder.Default
    List<AuthEntity> authEntityList = new ArrayList<>();

    public UserDTO toDTO(){
        ArrayList<TodoDTO> openDTOList = new ArrayList<>();
        ArrayList<TodoDTO> closeDTOList = new ArrayList<>();
        ArrayList<AuthDTO> authDTOList = new ArrayList<>();

        for(TodoEntity entity : openList){
            openDTOList.add(entity.toDTO());
        }

        for(TodoEntity entity : closeList){
            closeDTOList.add(entity.toDTO());
        }

        for(AuthEntity entity : authEntityList){
            authDTOList.add(entity.toDTO());
        }

        return UserDTO.builder().uId(this.uId).uName(this.uName).uEmail(this.uEmail).uRole(this.uRole)
                .openList(openDTOList).closeList(closeDTOList).authList(authDTOList).build();
    }
}
