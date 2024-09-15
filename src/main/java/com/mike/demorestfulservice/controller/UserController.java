package com.mike.demorestfulservice.controller;

import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    @Cacheable(value = "user", key = "#id")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) throws AuthenticationException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @Cacheable(value = "user", key = "#email")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) throws AuthenticationException {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
}
