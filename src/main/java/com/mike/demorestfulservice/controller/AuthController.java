package com.mike.demorestfulservice.controller;

import com.mike.demorestfulservice.dto.JwtAuthenticationDto;
import com.mike.demorestfulservice.dto.RefreshTokenDto;
import com.mike.demorestfulservice.dto.UserCredentialsDto;
import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Registration and authentication")
public class AuthController {

    private final UserService userService;

    @PostMapping("/registration")
    @Operation(summary = "Registration")
    public String register(@RequestBody @Valid UserDto userDto) throws javax.naming.AuthenticationException {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<JwtAuthenticationDto> login(@RequestBody @Valid UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.login(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (javax.naming.AuthenticationException e) {
            throw new RuntimeException("Authentication failed" + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT token")
    public JwtAuthenticationDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Successful logout", HttpStatus.OK);
    }
}
