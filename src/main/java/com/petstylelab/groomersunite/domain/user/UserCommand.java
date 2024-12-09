package com.petstylelab.groomersunite.domain.user;

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
        private final String nickname;
        private final String loginId;
        private final String currentPassword;
        private final String newPassword;

        public ModifyUserRequest(
                String nickname,
                String loginId,
                String currentPassword,
                String newPassword
        ) {
            this.nickname = nickname;
            this.loginId = loginId;
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }
    }

    @Getter
    @ToString
    public class ModifyPasswordRequest {
        private final String loginId;
        private final String newPassword;
        private final String confirmPassword;

        public ModifyPasswordRequest(
                String loginId,
                String newPassword,
                String confirmPassword
        ) {
            this.loginId = loginId;
            this.newPassword = newPassword;
            this.confirmPassword = confirmPassword;
        }
    }
}
