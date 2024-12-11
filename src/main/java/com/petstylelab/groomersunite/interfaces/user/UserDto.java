package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.domain.user.Role;
import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public class UserDto {

    @Getter
    @ToString
    public static class RegisterRequest {
        private String loginId;
        private String email;
        private String password;
        private String nickname;

        public UserCommand.RegisterUserRequest toCommand() {
            return UserCommand.RegisterUserRequest.builder()
                    .loginId(loginId)
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class RegisterResponse {
        private final String loginId;
        private final String email;
        private final String nickname;
        private final String password;
        private final Role role;
        private final LocalDate registrationDate;


        public RegisterResponse(UserInfo userInfo) {
            this.loginId = userInfo.getLoginId();
            this.email = userInfo.getEmail();
            this.nickname = userInfo.getNickname();
            this.password = userInfo.getPassword();
            this.role = userInfo.getRole();
            this.registrationDate = userInfo.getRegistrationDate();
        }
    }
}
