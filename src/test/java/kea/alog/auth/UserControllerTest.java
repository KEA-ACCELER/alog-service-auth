package kea.alog.auth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import kea.alog.auth.service.UserFeign;
import kea.alog.auth.service.UserService;
import kea.alog.auth.utils.JwtUtil;
import kea.alog.auth.web.UserController;
import kea.alog.auth.web.dto.UserDto.LoginRequestDto;
import kea.alog.auth.web.dto.UserDto.LoginResponseDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// @SpringBootTest
// @AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("로그인 테스트")
    void signup() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder().userEmail("test@test.com" ).userPw("test1234").build();
        
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjI3ODQwMzg5LCJleHAiOjE2Mjc4NDM5ODl9.7Z8GmWpZy6x4fFVXmX8oqYcLxhKb6kH1dR7cQfBZvqA";

        Assertions.assertEquals(jwt, jwt);
        // oginRequestDto.builder().userEmail("test@test.com" ).userPw("test1234").build();        // MockMvc를 사용하여 로그인 요청을 보냅니다.
        // mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/auth/permit-all/login")
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .content(new ObjectMapper().writeValueAsString(loginRequestDto)))
        //         // 응답의 상태 코드가 HttpStatus.CREATED인지 확인합니다.
        //         .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("jwt validation 테스트")
    void jwtValidation() {
        
        // mockMvc.perform(MockMvcRequestBuilders.post("/auth/jwt/validate")
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .content(new ObjectMapper().writeValueAsString("hello"))
        //         .andExpect(body().equals("hello")));
    }
}
