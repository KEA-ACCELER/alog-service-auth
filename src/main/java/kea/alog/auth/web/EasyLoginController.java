package kea.alog.auth.web;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.AuthorizePayloadsSpec.Access;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@RequestMapping("/auth/permit-all/github")
@RestController
@Slf4j
public class EasyLoginController {

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    // 서버에서 액세스 토큰 요청하기
    @Operation(summary = "깃허브 로그인", description = "인가토큰을 받아서 깃허브 리소스 서버에 액세스 토큰을 요청 & 깃허브 리소스 서버에서 사용자 정보를 받아옵니다.")
    @GetMapping("")
    public ResponseEntity<String> loginWithGithubCallback(@RequestParam String code)  {
        // RestTemplate 객체를 사용하여 깃허브 리소스 서버에 POST 요청을 보냅니다.
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("code", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate
                .postForEntity("https://github.com/login/oauth/access_token", request, String.class);
        log.info("response : {}", response.getBody());
        ObjectMapper mapper = new ObjectMapper();
        String accessToken = "";
        // 문자열을 ObjectNode로 변환
        try {
            ObjectNode objectNode = mapper.readValue(response.getBody(), ObjectNode.class);
            accessToken = objectNode.get("access_token").asText();
        }catch (Exception e){
            log.error("error : {}", e.getMessage());
        }
        
        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers2.setBearerAuth(accessToken);
        HttpEntity<?> request2 = new HttpEntity<>(headers);
        ResponseEntity<String> userInfoResponse = restTemplate2.exchange("https://api.github.com/user",
                HttpMethod.GET, request2, String.class);
        String userInfo = userInfoResponse.getBody();
    
        try {
            ObjectNode email = mapper.readValue(userInfo, ObjectNode.class);
            return ResponseEntity.ok(email.get("email").asText());
        }catch (Exception e){
            log.error("error : {}", e.getMessage());
        }
        

        return ResponseEntity.ok("Invalid User Info");

        //
    }

    // 액세스 토큰으로 유저 정보 요청하기
    // @GetMapping("/user")
    // public ResponseEntity<String> getUserInfo(@RequestParam String accessToken) {
    //     RestTemplate restTemplate = new RestTemplate();
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    //     headers.setBearerAuth(accessToken);
    //     HttpEntity<?> request = new HttpEntity<>(headers);
    //     ResponseEntity<String> userInfoResponse = restTemplate.exchange("https://api.github.com/user",
    //             HttpMethod.GET, request, String.class);
    //     String userInfo = userInfoResponse.getBody();
    //     ObjectMapper mapper = new ObjectMapper();
    //     try {
    //         ObjectNode email = mapper.readValue(userInfo, ObjectNode.class);
    //         return ResponseEntity.ok(email.get("email").asText());
    //     }catch (Exception e){
    //         log.error("error : {}", e.getMessage());
    //     }
    //     return ResponseEntity.ok("Invalid user Info");
    // }

}
