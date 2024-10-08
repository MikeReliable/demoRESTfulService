package com.mike.demorestfulservice.controller;

import com.mike.demorestfulservice.dto.CommentDto;
import com.mike.demorestfulservice.dto.TaskCommentsDto;
import com.mike.demorestfulservice.dto.TaskDto;
import com.mike.demorestfulservice.dto.TaskListResponseDto;
import com.mike.demorestfulservice.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
@Tag(name = "Tasks", description = "Methods of working with tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/author/{userId}")
    @Operation(summary = "Get all tasks of the Author by Id")
    public ResponseEntity<TaskListResponseDto> getAllTasksByAuthor(@PathVariable Long userId,
                                                                   @MatrixVariable(value = "fields", pathVar = "id", required = false) List<String> fields,
                                                                   @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                   @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
                                                                   @RequestParam(value = "priority", required = false) String priority,
                                                                   @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(taskService.getAllTasksByAuthor(userId, fields, pageNo, pageSize, priority, status), HttpStatus.OK);
    }

    @GetMapping("/executor/{userId}")
    @Operation(summary = "Get all tasks of the Executor by Id")
    public ResponseEntity<TaskListResponseDto> getAllTasksByExecutor(@PathVariable Long userId,
                                                                     @MatrixVariable(value = "fields", pathVar = "id", required = false) List<String> fields,
                                                                     @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                     @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
                                                                     @RequestParam(value = "priority", required = false) String priority,
                                                                     @RequestParam(value = "status", required = false) String status) {
        return new ResponseEntity<>(taskService.getAllTasksByExecutor(userId, fields, pageNo, pageSize, priority, status), HttpStatus.OK);
    }

    @PostMapping("/{userId}/create")
    @Operation(summary = "Create task")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskDto> createTask(@RequestHeader("authorization") String token,
                                              @PathVariable Long userId,
                                              @RequestBody TaskDto taskDto) throws Exception {
        return new ResponseEntity<>(taskService.createTask(token, userId, taskDto), HttpStatus.CREATED);
    }


    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by Id")
    @Cacheable(value = "task", key = "#taskId")
    public ResponseEntity<TaskCommentsDto> getTask(@PathVariable Long taskId) throws Exception {
        return new ResponseEntity<>(taskService.getTask(taskId), HttpStatus.OK);
    }

    @PutMapping("/author/{taskId}")
    @Operation(summary = "Update task by Author")
    @CachePut(value = "task", key = "#taskId")
    public ResponseEntity<TaskDto> updateTaskByAuthor(@RequestHeader("authorization") String token,
                                                      @PathVariable Long taskId,
                                                      @RequestBody @Valid TaskDto taskDto) throws Exception {
        return new ResponseEntity<>(taskService.updateTaskByAuthor(token, taskId, taskDto), HttpStatus.OK);
    }

    @PutMapping("/executor/{taskId}")
    @Operation(summary = "Update task by Executor")
    @CachePut(value = "task", key = "#taskId")
    public ResponseEntity<TaskDto> updateTaskByExecutor(@RequestHeader("authorization") String token,
                                                        @PathVariable Long taskId,
                                                        @RequestBody @Valid TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTaskByExecutor(token, taskId, taskDto), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{taskId}")
    @Operation(summary = "Delete task by Author")
    @CacheEvict(value = "task", key = "#taskId")
    public ResponseEntity<String> deleteTask(@RequestHeader("authorization") String token,
                                             @PathVariable Long userId,
                                             @PathVariable Long taskId) {
        taskService.deleteTask(token, userId, taskId);
        return new ResponseEntity<>("Task completely deleted", HttpStatus.OK);
    }

    @PostMapping("/{taskId}/comment")
    @Operation(summary = "Create a comment")
    @CachePut(value = "task", key = "#taskId")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskCommentsDto> createComment(@RequestHeader("authorization") String token,
                                                         @PathVariable Long taskId,
                                                         @RequestBody @Valid CommentDto commentDto) {
        return new ResponseEntity<>(taskService.createComment(token, taskId, commentDto), HttpStatus.CREATED);
    }

}
