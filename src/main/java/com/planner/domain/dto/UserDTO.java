package com.planner.domain.dto;

import com.planner.domain.entity.user.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements UserDetails{
    private int uNo;
    private String uId;
    private String uPassword;
    private String uName;
    private String uEmail;
    private String uRole;
    private Set<GrantedAuthority> authorities;

    //FK
    private List<TodoDTO> openList;
    private List<TodoDTO> closeList;
    private List<AuthDTO> authList;

    public UserEntity toEntity(){
        return UserEntity.builder().uNo(this.uNo).uId(this.uId).uPassword(this.uPassword)
                .uName(this.uName).uEmail(this.uEmail).uRole(this.uRole).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.uPassword;
    }

    @Override
    public String getUsername() {
        return this.uId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
