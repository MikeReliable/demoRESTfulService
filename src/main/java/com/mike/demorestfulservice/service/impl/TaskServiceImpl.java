package com.mike.demorestfulservice.service.impl;

import com.mike.demorestfulservice.dto.CommentDto;
import com.mike.demorestfulservice.dto.TaskCommentsDto;
import com.mike.demorestfulservice.dto.TaskDto;
import com.mike.demorestfulservice.dto.TaskListResponseDto;
import com.mike.demorestfulservice.entity.*;
import com.mike.demorestfulservice.mapper.EntityMapperImpl;
import com.mike.demorestfulservice.repository.CommentRepository;
import com.mike.demorestfulservice.repository.TaskRepository;
import com.mike.demorestfulservice.repository.UserRepository;
import com.mike.demorestfulservice.security.jwt.JwtService;
import com.mike.demorestfulservice.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private JwtService jwtService;
    private EntityMapperImpl mapper;

    @Override
    public TaskListResponseDto getAllTasksByAuthor(Long userId, List<String> fields, int pageNo, int pageSize, String priority, String status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Task> tasks = taskRepository.findAllByAuthor_UserId(userId, pageable);
        return getTaskListToTaskListResponseDto(tasks, fields, priority, status);
    }

    @Override
    public TaskListResponseDto getAllTasksByExecutor(Long userId, List<String> fields, int pageNo, int pageSize, String priority, String status) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Task> tasks = taskRepository.findAllByExecutor_UserId(userId, pageable);
        return getTaskListToTaskListResponseDto(tasks, fields, priority, status);
    }

    @Override
    public TaskDto createTask(String token, Long userId, TaskDto taskDto) {
        String email = jwtService.getEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email %s not found", email)));
        User userPath = userRepository.findByUserId(userId).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with Id %s not found", userId)));
        if (!user.getUserId().equals(userPath.getUserId())) {
            throw new EntityNotFoundException("only author can create the task");
        }
        Task task = Task.builder()
                .text(taskDto.getText())
                .priority(Priority.valueOf(taskDto.getPriority()))
                .status(Status.WAITING)
                .author(user)
                .build();
        Task newTask = taskRepository.save(task);
        TaskDto newTaskDto = mapper.toTaskDto(newTask);
        newTaskDto.setAuthorId(newTask.getAuthor().getUserId().toString());
        return newTaskDto;
    }

    @Override
    public TaskCommentsDto getTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with Id %s not found", taskId)));
        return getTaskCommentsDto(task);
    }

    @Override
    public TaskDto updateTaskByAuthor(String token, Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with Id %s not found", taskId)));
        String email = jwtService.getEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email %s not found", email)));
        if (!task.getAuthor().getUserId().equals(user.getUserId())) {
            throw new EntityNotFoundException("only the author can update the task priority and appoint an executor");
        }
        if (taskDto.getText() != null) {
            task.setText(taskDto.getText());
        }
        if (taskDto.getPriority() != null) {
            task.setPriority(Priority.valueOf(taskDto.getPriority()));
        }
        if (taskDto.getExecutorId() != null) {
            User executor = userRepository.findByUserId(Long.valueOf(taskDto.getExecutorId())).orElseThrow(() ->
                    new EntityNotFoundException(String.format("User with Id %s not found", taskDto.getExecutorId())));
            task.setExecutor(executor);
        }
        Task newTask = taskRepository.save(task);
        TaskDto newTaskDto = mapper.toTaskDto(newTask);
        newTaskDto.setAuthorId(newTask.getExecutor().getUserId().toString());
        if (newTask.getExecutor() != null) {
            newTaskDto.setExecutorId(newTask.getExecutor().getUserId().toString());
        }
        return newTaskDto;
    }

    @Override
    public TaskDto updateTaskByExecutor(String token, Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with Id %s not found", taskId)));
        if (task.getExecutor() == null) {
            throw new EntityNotFoundException("there is no executor assigned to the task");
        }
        String email = jwtService.getEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email %s not found", email)));
        if (!task.getExecutor().getUserId().equals(user.getUserId())) {
            throw new EntityNotFoundException("only the author can update the task priority and appoint an executor");
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(Status.valueOf(taskDto.getStatus()));
        }
        Task newTask = taskRepository.save(task);
        TaskDto newTaskDto = mapper.toTaskDto(newTask);
        newTaskDto.setAuthorId(newTask.getExecutor().getUserId().toString());
        newTaskDto.setExecutorId(newTask.getExecutor().getUserId().toString());
        return newTaskDto;
    }

    @Override
    public void deleteTask(String token, Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with Id %s not found", taskId)));
        String email = jwtService.getEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email %s not found", email)));
        if (userId.equals(task.getAuthor().getUserId()) && userId.equals(user.getUserId())) {
            taskRepository.delete(task);
        } else {
            throw new EntityNotFoundException("only the author can delete the task");
        }
    }

    @Override
    public TaskCommentsDto createComment(String token, Long taskId, CommentDto commentDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Task with Id %s not found", taskId)));
        String email = jwtService.getEmailFromToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with email %s not found", email)));
        if (user.getUserId().equals(task.getAuthor().getUserId())) {
            throw new EntityNotFoundException("author cannot leave comments");
        }
        Comment comment = mapper.toComment(commentDto);
        comment.setAuthor(user);
        comment.setTask(task);
        commentRepository.save(comment);
        return getTaskCommentsDto(task);
    }

    private TaskListResponseDto getTaskListToTaskListResponseDto(Page<Task> tasks, List<String> fields, String priority, String status) {
        List<Task> taskList = tasks.getContent();
        if (priority != null) {
            taskList = taskList.stream().filter(t -> t.getPriority().toString().equals(priority)).collect(Collectors.toList());
        }
        if (status != null) {
            taskList = taskList.stream().filter(t -> t.getStatus().toString().equals(status)).collect(Collectors.toList());
        }
        if (fields != null) {
            taskList = taskList.stream().map(t -> filterFields(t, fields)).collect(Collectors.toList());
        }
        List<TaskDto> content = new ArrayList<>();
        for (Task task : taskList) {
            TaskDto taskDto = new TaskDto();
            taskDto.setTaskId(task.getTaskId().toString());
            taskDto.setText(task.getText());
            taskDto.setStatus(task.getStatus().toString());
            taskDto.setPriority(task.getPriority().toString());
            taskDto.setAuthorId(task.getAuthor().getUserId().toString());
            if (task.getExecutor() != null) {
                taskDto.setExecutorId(task.getExecutor().getUserId().toString());
            }
            content.add(taskDto);
        }
        TaskListResponseDto responseDto = new TaskListResponseDto();
        responseDto.setTaskList(content);
        responseDto.setPageNo(tasks.getNumber());
        responseDto.setPageSize(tasks.getSize());
        responseDto.setTotalElements(tasks.getTotalElements());
        responseDto.setTotalPages(tasks.getTotalPages());
        responseDto.setLast(tasks.isLast());
        return responseDto;
    }

    private Task filterFields(Task task, List<String> fields) {
        Task filteredTask = new Task();
        for (String field : fields) {
            try {
                Field f = task.getClass().getDeclaredField(field);
                f.setAccessible(true);
                f.set(filteredTask, f.get(field));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filteredTask;
    }

    private TaskCommentsDto getTaskCommentsDto(Task task) {
        TaskCommentsDto taskCommentsDto = mapper.toTaskCommentDto(task);
        taskCommentsDto.setAuthorId(task.getAuthor().getUserId().toString());
        if (task.getExecutor() != null) {
            taskCommentsDto.setExecutorId(task.getExecutor().getUserId().toString());
        }
        List<CommentDto> commentDtoList = new ArrayList<>();
        List<Comment> commentList = task.getCommentList();
        for (Comment comment : commentList) {
            CommentDto commentDto = mapper.toCommentDto(comment);
            commentDtoList.add(commentDto);
        }
        taskCommentsDto.setComments(commentDtoList);
        return taskCommentsDto;
    }
}
