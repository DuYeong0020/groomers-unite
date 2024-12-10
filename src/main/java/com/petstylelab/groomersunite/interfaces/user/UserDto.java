package com.petstylelab.groomersunite.interfaces.user;

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
    }
}
