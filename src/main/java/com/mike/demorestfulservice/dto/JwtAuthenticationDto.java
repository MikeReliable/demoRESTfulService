package com.mike.demorestfulservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Tokens")
public class JwtAuthenticationDto {

    private String token;
    private String refreshToken;
}
