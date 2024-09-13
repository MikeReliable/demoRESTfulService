package com.mike.demorestfulservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    String userId;
    @NotBlank
    @Size(min = 3, max = 20, message = "FirstName length must be from 3 to 20 characters")
    String firstName;
    @NotBlank
    @Size(min = 3, max = 20, message = "LastName length must be from 3 to 20 characters")
    String lastName;
    @Email(message = "Email address has invalid format",
            regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    String email;
    @NotBlank
    @Size(min = 6, max = 20, message = "Password length must be from 6 to 20 characters")
    String password;
}
