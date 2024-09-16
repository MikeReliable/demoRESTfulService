package com.mike.demorestfulservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Schema(description = "Task information")
public class TaskDto {

    String taskId;
    @Size(min = 10, message = "Task text minimum length is 10 characters")
    String text;
    String priority;
    String status;
    @Pattern(regexp = "[0-9]+$", message = "Id must be a number")
    String AuthorId;
    @Pattern(regexp = "[0-9]+$", message = "Id must be a number")
    String executorId;
}
