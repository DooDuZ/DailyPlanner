package com.planner.domain.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private int uNo;
    private String uId;
    private String uName;
    private String uEmail;
    private String uRole;
}
