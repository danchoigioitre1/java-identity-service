package com.example.demo.service;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@Slf4j
@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;
    @BeforeEach
    void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserCreationRequest.builder()
                .username("meo100k")
                .firstName("NVA")
                .lastName("NVA2")
                .password("12345678")
                .dob(dob)
                .build();
        userResponse = UserResponse.builder()
                .id("c551b07dea47")
                .username("meo100k")
                .firstName("NVA")
                .lastName("NVA2")
                .dob(dob)
                .build();
        user = User.builder()
                .id("c551b07dea47")
                .username("meo100k")
                .firstName("NVA")
                .lastName("NVA2")
                .dob(dob)
                .build();
    }
    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsUserByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        // WHEN
        UserResponse response = userService.createUser(request);
        // THEN
        log.info("NVA: " + response);
        Assertions.assertThat(response.getId()).isEqualTo("c551b07dea47");
        Assertions.assertThat(response.getUsername()).isEqualTo("meo100k");
    }
    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsUserByUsername(anyString())).thenReturn(true);
        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));
        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "meo100k")
    void getMyInfo_valid_success() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("meo100k");
        Assertions.assertThat(response.getId()).isEqualTo("c551b07dea47");
    }

    @Test
    @WithMockUser(username = "meo100k")
    void getMyInfo_userNotFound_error() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}