package kea.alog.auth.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import kea.alog.auth.web.dto.UserDto.LoginRequestDto;
import kea.alog.auth.web.dto.UserDto.LoginResponseDto;



@FeignClient(
    name="user-service",
    url="${user.service.url}"
)
public interface UserFeign {
    
    @GetMapping(path="/info")
    public Optional<Object> GetUserInfo(@RequestHeader("authorization") String jwt);

    @PostMapping(path="/login")
    public Optional<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto);
}

