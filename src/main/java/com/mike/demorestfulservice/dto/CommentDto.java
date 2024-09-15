package com.mike.demorestfulservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Comment information")
public class CommentDto {

    String commentId;
    @NotBlank
    @Size(min = 5, message = "Comment length minimum 5 characters")
    String text;
    String authorId;
}
