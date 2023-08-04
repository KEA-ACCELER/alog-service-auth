package kea.alog.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kea.alog.auth.service.UserService;
import kea.alog.auth.web.dto.UserDto.LoginRequestDto;
@RestController
@RequestMapping("/auth")
public class UserController {
    
    @Autowired
    UserService userService;

    @Operation(summary = "로그인", description = "로그인")
    @ApiResponse(responseCode = "201", description = "return : jwt")
    @PostMapping("/permit-all/login")
    public ResponseEntity<String> signup(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(loginRequestDto));
    }
    
    @PostMapping("/jwt/validate")
    public ResponseEntity<Boolean> validate() {
        return ResponseEntity.ok(true);
    }

}


