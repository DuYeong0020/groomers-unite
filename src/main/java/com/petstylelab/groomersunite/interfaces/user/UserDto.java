package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.domain.user.UserCommand;
import lombok.Getter;
import lombok.ToString;

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
}
