package com.mike.demorestfulservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationDto {

    private String token;
    private String refreshToken;
}
