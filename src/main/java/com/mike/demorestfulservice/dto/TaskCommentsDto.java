package com.mike.demorestfulservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskCommentsDto {

    String taskId;
    String text;
    String priority;
    String status;
    String authorId;
    String executorId;
    List<CommentDto> comments;
}
