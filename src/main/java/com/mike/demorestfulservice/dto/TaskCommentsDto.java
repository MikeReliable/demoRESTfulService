package com.mike.demorestfulservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Task with all comments information")
public class TaskCommentsDto {

    String taskId;
    String text;
    String priority;
    String status;
    String authorId;
    String executorId;
    List<CommentDto> comments;
}
