package com.mike.demorestfulservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    String commentId;
    @NotBlank
    @Size(min = 5, message = "Comment length minimum 5 characters")
    String text;
    String authorId;
}
