package com.petstylelab.groomersunite.domain.user;

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
    }

    @Getter
    @ToString
    public static class ModifyUserRequest {
        private final String nickname;
        private final String currentPassword;
        private final String newPassword;

        public ModifyUserRequest(
                String nickname,
                String currentPassword,
                String newPassword
        ) {
            this.nickname = nickname;
            this.currentPassword = currentPassword;
            this.newPassword = newPassword;
        }
    }


}
