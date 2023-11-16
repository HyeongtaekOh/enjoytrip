package com.ssafy.enjoytrip.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SignupVo {

    private String username;
    private String password;
    private String email;
}
