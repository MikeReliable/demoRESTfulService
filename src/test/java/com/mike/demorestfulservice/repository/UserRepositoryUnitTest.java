package com.mike.demorestfulservice.repository;

import com.mike.demorestfulservice.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserRepositoryUnitTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void findByUserEmail_payLoadIsValid_ReturnsValidResponse() {
        //given
        var user = User.builder()
                .userId(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("Ivanov@mailil.com")
                .password("password")
                .build();
        Optional<User> userOptional = Optional.of(user);
        doReturn(userOptional).when(this.userRepository).findByEmail(user.getEmail());
        //when
        assertThat(userRepository.findByEmail("Ivanov@mailil.com")
                //then
                .isPresent())
                .isTrue();
        assertEquals(user, userRepository.findByEmail("Ivanov@mailil.com").get());
    }

    @Test
    void findByUserEmail_payLoadIsInvalid_ReturnsInvalidResponse() {
        assertThat(userRepository.findByEmail("Ivan@mailil.com")
                .isPresent())
                .isFalse();
    }
}