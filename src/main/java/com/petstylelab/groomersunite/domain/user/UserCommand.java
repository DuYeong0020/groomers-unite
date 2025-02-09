package com.petstylelab.groomersunite.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public class UserCommand {

    @Getter
    @ToString
    public static class RegisterUserRequest {
        private final String loginId;
        private final String email;
        private final String password;
        private final String nickname;

        @Builder
        public RegisterUserRequest(
                String loginId,
                String email,
                String password,
                String nickname
        ) {
            this.loginId = loginId;
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

        public User toEntity(String encodePassword, Role role, LocalDate registrationDate) {
            return User.builder()
                    .loginId(this.loginId)
                    .email(this.email)
                    .password(encodePassword)
                    .nickname(this.nickname)
                    .role(role)
                    .registrationDate(registrationDate)
                    .build();
        }

    }

    @Getter
    @ToString
    public static class ModifyUserRequest {
        private final Long userId;
        private final String nickname;
        private final String currentPassword;
        private final String newPassword;

        @Builder
        public ModifyUserRequest(
                String nickname,
                Long userId,
                String currentPassword,
                String newPassword
        ) {
            this.nickname = nickname;
            this.userId = userId;
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }
    }

    @Getter
    @ToString
    public static class ModifyPasswordRequest {
        private final String email;
        private final String newPassword;
        private final String confirmPassword;

        @Builder
        public ModifyPasswordRequest(
                String email,
                String newPassword,
                String confirmPassword
        ) {
            this.email = email;
            this.newPassword = newPassword;
            this.confirmPassword = confirmPassword;
        }
    }

    @Getter
    @ToString
    public static class AuthenticateUserRequest {
        private final String loginId;
        private final String password;

        @Builder
        public AuthenticateUserRequest(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }
    }
}
