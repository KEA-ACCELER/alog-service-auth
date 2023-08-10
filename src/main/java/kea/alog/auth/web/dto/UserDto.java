package kea.alog.auth.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginRequestDto{
        private String userEmail;
        private String userPw;

        @Builder
        public LoginRequestDto(String userEmail, String userPw){
            this.userEmail = userEmail;
            this.userPw = userPw;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginResponseDto{
        private Long userPk;
        private String userNN;

        @Builder
        public LoginResponseDto(Long userPk, String userNN){
            this.userPk = userPk;
            this.userNN = userNN;
        }
    }
}

