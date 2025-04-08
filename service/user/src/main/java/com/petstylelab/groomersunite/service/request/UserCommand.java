package com.petstylelab.groomersunite.service.request;

import com.petstylelab.groomersunite.entity.Role;
import com.petstylelab.groomersunite.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserCommand {

    @Getter
    @ToString
    public static class RegisterUserRequest {
        private final String loginId;
        private final String email;
        private final String password;
        private final String nickname;

        @Builder
        public RegisterUserRequest(String loginId, String email, String password, String nickname) {
            this.loginId = loginId;
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

        public User toEntity(String password, Role role) {
            return User.builder()
                    .loginId(loginId)
                    .email(email)
                    .nickname(nickname)
                    .password(password)
                    .role(role)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class ModifyUserRequest {
        private final String loginId;
        private final String email;
        private final String nickname;
        private final String password;

        @Builder
        public ModifyUserRequest(String loginId, String email, String nickname, String password) {
            this.loginId = loginId;
            this.email = email;
            this.nickname = nickname;
            this.password = password;
        }
    }

    @Getter
    @ToString
    public static class ModifyPasswordRequest {
        private final Long userId;
        private final String password;

        @Builder
        public ModifyPasswordRequest(Long userId, String password) {
            this.userId = userId;
            this.password = password;
        }
    }

}
