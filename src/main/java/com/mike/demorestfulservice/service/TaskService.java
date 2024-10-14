package com.mike.demorestfulservice.service;

import com.mike.demorestfulservice.dto.CommentDto;
import com.mike.demorestfulservice.dto.TaskCommentsDto;
import com.mike.demorestfulservice.dto.TaskDto;
import com.mike.demorestfulservice.dto.TaskListResponseDto;

public interface TaskService {

    TaskListResponseDto getAllTasksByAuthor(Long userId, int pageNo, int pageSize, String priority, String status);

    TaskListResponseDto getAllTasksByExecutor(Long userId, int pageNo, int pageSize, String priority, String status);

    TaskDto createTask(String token, Long userId, TaskDto taskDto) throws Exception;

    TaskCommentsDto getTask(Long taskId) throws Exception;

    TaskDto updateTaskByAuthor(String token, Long taskId, TaskDto taskDto) throws Exception;

    TaskDto updateTaskByExecutor(String token, Long taskId, TaskDto taskDto);

    void deleteTask(String token, Long taskId);

    TaskCommentsDto createComment(String token, Long taskId, CommentDto commentDto);
}
