package com.mike.demorestfulservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import com.mike.demorestfulservice.dto.RefreshTokenDto;
import com.mike.demorestfulservice.dto.UserCredentialsDto;
import com.mike.demorestfulservice.dto.UserDto;
import com.mike.demorestfulservice.entity.View;
import com.mike.demorestfulservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Sql(value = {"/insert-data-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    AuthController authController;

    @Test
    @JsonView(View.REST.class)
    public void register_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userDto = UserDto.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("Ivanov@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userDto);
        //when
        mockMvc.perform(post("/auth/registration").contentType(MediaType.APPLICATION_JSON).content(json))
                //then
                .andExpectAll(status().isOk())
//                .andExpect(content().string(containsString("User added")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void login_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo2@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        //when
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void refreshToken_payLoadIsValid_ReturnsValidResponse() throws Exception {
        //given
        var userCredentialsDto = UserCredentialsDto.builder()
                .email("Demo1@mail.com")
                .password("password")
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(userCredentialsDto);
        String refreshToken = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResponse().getContentAsString().substring(169, 310);
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();
        refreshTokenDto.setRefreshToken(refreshToken);
        Gson content = new Gson();
        String jsonContent = content.toJson(refreshTokenDto);
        //when
        mockMvc.perform(post("/auth/refresh").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andDo(print())
                .andReturn();
    }

    @Test
    void logout_payLoadIsValid_ReturnsValidResponse() throws Exception {
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
        mockMvc.perform(post("/auth/logout").contentType(MediaType.APPLICATION_JSON).content(token))
                //then
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successful logout")))
                .andDo(print())
                .andReturn();
    }
}