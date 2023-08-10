package kea.alog.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.auth.utils.JwtUtil;
import kea.alog.auth.web.dto.UserDto.LoginRequestDto;
import kea.alog.auth.web.dto.UserDto.LoginResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    @Autowired
    UserFeign userFeign;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expireMS = 1000L * 60 * 60 * 24 * 7;// 1주일

    @Transactional
    public String login(LoginRequestDto loginRequestDto){
        
        Optional<LoginResponseDto> isSuccessed = userFeign.login(loginRequestDto);
        if (isSuccessed.isEmpty()) {
            return "login failed";
        }
        
        String token = JwtUtil.createJwt(isSuccessed.get().getUserPk(), isSuccessed.get().getUserNN(), loginRequestDto.getUserEmail(), secretKey, expireMS);
        System.out.println("token  " + token);
        return token;
    }

    @Transactional 
    public String isRegistered (String userEmail) {
        Optional<LoginResponseDto> isRegistered = userFeign.isConfirmEmail(userEmail);
        if (isRegistered.isEmpty()) {
            return "email " + userEmail;
        }
        LoginResponseDto loginResponseDto = isRegistered.get();
        return "jwt "+ JwtUtil.createJwt(loginResponseDto.getUserPk(), loginResponseDto.getUserNN(), userEmail, secretKey, expireMS);
    }


}
