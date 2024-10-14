package com.mike.demorestfulservice.controller;

import com.google.gson.Gson;
import com.mike.demorestfulservice.dto.CommentDto;
import com.mike.demorestfulservice.dto.TaskDto;
import com.mike.demorestfulservice.dto.UserCredentialsDto;
import com.mike.demorestfulservice.entity.Priority;
import com.mike.demorestfulservice.entity.Task;
import com.mike.demorestfulservice.entity.User;
import com.mike.demorestfulservice.repository.TaskRepository;
import com.mike.demorestfulservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
//@Sql(value = {"/insert-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class TaskControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAllTasksByAuthor_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        String token = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString().substring(10, 151);
        //when
        mockMvc.perform(get("/tasks/authors/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Do the demo task")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getAllTasksByExecutor_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        String token = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString().substring(10, 151);
        //when
        mockMvc.perform(get("/tasks/executors/2").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Do the demo task")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void getTask_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo2@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        String token = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString().substring(10, 151);
        //when
        mockMvc.perform(get("/tasks/3").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Do the demo task")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void createTask_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userId = "1";
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        var request = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();
        String token = request.getResponse().getContentAsString().substring(10, 151);

        var taskDto = TaskDto.builder()
                .text("This is a test text")
                .priority(Priority.LOW.toString())
                .build();
        Gson gsonRequest = new Gson();
        String jsonRequest = gsonRequest.toJson(taskDto);
        //when
        mockMvc.perform(post("/tasks/" + userId)
                        .header("authorization", "Bearer " + token)
                        .param("userId", userId)
                        .param("token", token)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("This is a test text")))
                .andExpect(content().string(containsString("WAITING")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void updateTaskByAuthor_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var taskId = "1";
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        var request = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();
        String token = request.getResponse().getContentAsString().substring(10, 151);

        TaskDto taskDto = TaskDto.builder()
                .text("This is a test text")
                .priority(Priority.HIGH.toString())
                .executorId("2")
                .build();
        Gson gsonRequest = new Gson();
        String jsonRequest = gsonRequest.toJson(taskDto);
        //when
        mockMvc.perform(put("/tasks/" + taskId + "/authors")
                        .header("authorization", "Bearer " + token)
                        .param("taskId", taskId)
                        .param("token", token)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is a test text")))
                .andExpect(content().string(containsString("HIGH")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteTask_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userId = 1;
        User user = userRepository.findByUserId(1L).orElseThrow(() ->
                new Exception(String.format("User with Id %s not found", 1)));
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        var request = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();
        String token = request.getResponse().getContentAsString().substring(10, 151);
        Task task = Task.builder()
                .text("This is a test text")
                .priority(Priority.LOW)
                .author(user)
                .build();
        taskRepository.save(task);
        //when
        mockMvc.perform(delete("/tasks/" + task.getTaskId())
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Task completely deleted")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void createComment_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var taskId = "4";
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        var request = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn();
        String token = request.getResponse().getContentAsString().substring(10, 151);

        CommentDto commentDto = new CommentDto();
        commentDto.setText("This is a test comment");
        Gson gsonRequest = new Gson();
        String jsonRequest = gsonRequest.toJson(commentDto);
        //when
        mockMvc.perform(post("/tasks/" + taskId + "/comments")
                        .header("authorization", "Bearer " + token)
                        .param("taskId", taskId)
                        .param("token", token)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("This is a test comment")))
                .andDo(print())
                .andReturn();
    }
}