package com.petstylelab.groomersunite.domain.user;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserInfo {
    private final String loginId;
    private final String email;
    private final String password;
    private final String nickname;
    private final Role role;
    private final LocalDate registrationDate;

    public UserInfo(User user, LocalDate registrationDate) {
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.registrationDate = registrationDate;
    }
}
