package com.mike.demorestfulservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "Access and refresh tokens")
public class JwtAuthenticationDto {

    private String token;
    private String refreshToken;
}
