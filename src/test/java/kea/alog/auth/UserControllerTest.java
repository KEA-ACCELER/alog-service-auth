package kea.alog.auth;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.core.util.Json;
import kea.alog.auth.web.UserController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks // 테스트 대상 객체에 모킹된 객체들을 주입하기 위한 어노테이션
    private UserController userController; // 테스트 대상 객체

    @Mock // 모킹할 객체들을 선언하기 위한 어노테이션
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<String> responseEntity;

    @Test // 단위 테스트 메소드임을 나타내는 어노테이션
    public void testGetUserInfo() throws Exception {
        // given: 모킹할 객체들의 행위와 반환값을 설정하는 부분
        String accessToken = "test-token"; // 테스트용 액세스 토큰
        String userInfo = "{\"email\":\"test@test.com\"}"; // 테스트용 유저 정보
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(headers);
        when(restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, request, String.class)) // restTemplate의
                                                                                                          // exchange
                                                                                                          // 메소드가
                                                                                                          // 호출될 때
                .thenReturn(responseEntity); // responseEntity를 반환하도록 설정
        when(responseEntity.getBody()) // responseEntity의 getBody 메소드가 호출될 때
                .thenReturn(userInfo); // userInfo를 반환하도록 설정

        // when: 실제로 테스트할 메소드를 실행하는 부분
        ResponseEntity<String> result = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, request,
                String.class); // 액세스 토큰으로 유저 정보를 요청하는 메소드 실행

        ObjectMapper objectMapper = new ObjectMapper();
        String email = objectMapper.readValue(result.getBody(), ObjectNode.class).get("email").asText(); // 결과를 json으로
                                                                                                         // 변환
        //Then
        Assertions.assertEquals("test@test.com", email); // 결과의 바디가 이메일 주소인지 확인
    }

}