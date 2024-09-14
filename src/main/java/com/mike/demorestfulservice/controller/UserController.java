package com.mike.demorestfulservice.controller;

import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        try {
            UserDto userDto = userService.getUserById(id);
            userDto.setPassword(null);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw new RuntimeException("No user found with id=" + id);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        try {
            UserDto userDto = userService.getUserByEmail(email);
            userDto.setPassword(null);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw new RuntimeException("No user found with email=" + email);
        }
    }
}
