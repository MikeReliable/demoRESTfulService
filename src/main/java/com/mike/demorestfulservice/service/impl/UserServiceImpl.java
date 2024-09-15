package com.mike.demorestfulservice.service.impl;

import com.mike.demorestfulservice.dto.JwtAuthenticationDto;
import com.mike.demorestfulservice.dto.RefreshTokenDto;
import com.mike.demorestfulservice.dto.UserCredentialsDto;
import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.entity.Token;
import com.mike.demorestfulservice.entity.User;
import com.mike.demorestfulservice.mapper.EntityMapper;
import com.mike.demorestfulservice.repository.TokenRepository;
import com.mike.demorestfulservice.repository.UserRepository;
import com.mike.demorestfulservice.security.jwt.JwtService;
import com.mike.demorestfulservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final EntityMapper entityMapper;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String register(UserDto userDto) throws AuthenticationException {
        User user = entityMapper.toUser(userDto);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "This user is already exists";
        }
        if (user.getPassword().length() < 6) {
            throw new AuthenticationException("Password minimum length is 6 characters");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        jwtService.generateAuthToken(user.getEmail());
        return "User added";
    }

    @Override
    public JwtAuthenticationDto login(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = userRepository.findByEmail(userCredentialsDto.getEmail())
                .orElseThrow(() -> new AuthenticationException("Email or password is incorrect"));
        if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
            List<Token> tokenBlackList = tokenRepository.findAllByUser(user);
            for (Token token : tokenBlackList) {
                if (!jwtService.validateJwtToken(token.getToken())) {
                    tokenRepository.delete(token);
                }
            }
            return jwtService.generateAuthToken(user.getEmail());
        } else {
            throw new AuthenticationException("Email or password is incorrect");
        }
    }

    @Override
    public UserDto getUserById(String id) throws AuthenticationException {
        return entityMapper.toUserDto(userRepository.findByUserId(Long.valueOf(id))
                .orElseThrow(() -> new AuthenticationException("Email or password is incorrect")));
    }

    @Override
    public UserDto getUserByEmail(String email) throws AuthenticationException {
        return entityMapper.toUserDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Email or password is incorrect")));
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            String email = jwtService.getEmailFromToken(refreshToken);
            User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new EntityNotFoundException(String.format("User with email %s not found", email)));
            return jwtService.refreshAuthToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }
}
