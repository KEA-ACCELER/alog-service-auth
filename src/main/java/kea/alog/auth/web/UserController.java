package kea.alog.auth.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "인증된 이메일로 로그인", description = "깃허브 로그인으로 온 이메일을 확인하여 기존의 멤버면 로그인성공(jwt), 아니라면 그냥 email반환")
    @GetMapping("/permit-all/email-login")
    public ResponseEntity<String> isRegistered(@Parameter(description = "깃허브 로그인 후 받아온 유저 이메일") @RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.isRegistered(email));
    }


    @PostMapping("/jwt/validate")
    public ResponseEntity<Boolean> validate() {
        return ResponseEntity.ok(true);
    }



}


