package com.mike.demorestfulservice.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.mike.demorestfulservice.entity.View;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "User information")
public class UserDto {

    @JsonView(View.UI.class)
    String userId;
    @JsonView(View.UI.class)
    @NotBlank
    @Size(min = 3, max = 20, message = "FirstName length must be from 3 to 20 characters")
    String firstName;
    @JsonView(View.UI.class)
    @NotBlank
    @Size(min = 3, max = 20, message = "LastName length must be from 3 to 20 characters")
    String lastName;
    @JsonView(View.UI.class)
    @Email(message = "Email address has invalid format",
            regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    String email;
    @JsonView(View.UI.class)
    @NotBlank
    @Size(min = 6, max = 20, message = "Password length must be from 6 to 20 characters")
    String password;

    @JsonView(View.REST.class)
    public String getPassword() {
        return password;
    }
}
