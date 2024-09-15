package com.mike.demorestfulservice.service;

import com.mike.demorestfulservice.dto.JwtAuthenticationDto;
import com.mike.demorestfulservice.dto.RefreshTokenDto;
import com.mike.demorestfulservice.dto.UserCredentialsDto;
import com.mike.demorestfulservice.dto.UserDto;

import javax.naming.AuthenticationException;

public interface UserService {

    String register(UserDto userDto) throws AuthenticationException;

    JwtAuthenticationDto login(UserCredentialsDto userCredentialsDto) throws AuthenticationException;

    UserDto getUserById(String id) throws AuthenticationException;

    UserDto getUserByEmail(String email) throws AuthenticationException;

    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

}
