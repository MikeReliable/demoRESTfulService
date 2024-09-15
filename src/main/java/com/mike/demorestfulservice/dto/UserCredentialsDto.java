package com.mike.demorestfulservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Login information")
public class UserCredentialsDto {
    private String email;
    private String password;
}
