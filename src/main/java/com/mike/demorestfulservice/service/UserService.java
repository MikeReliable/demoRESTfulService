package com.mike.demorestfulservice.service;

import com.mike.demorestfulservice.dto.JwtAuthenticationDto;
import com.mike.demorestfulservice.dto.RefreshTokenDto;
import com.mike.demorestfulservice.dto.UserCredentialsDto;
import com.mike.demorestfulservice.dto.UserDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import javax.naming.AuthenticationException;

public interface UserService {

    String register(UserDto userDto) throws AuthenticationException;

    JwtAuthenticationDto login(UserCredentialsDto userCredentialsDto) throws AuthenticationException;

    UserDto getUserById(String id) throws ChangeSetPersister.NotFoundException;

    UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;

    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

}
