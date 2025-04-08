package com.petstylelab.groomersunite.service.response;

import com.petstylelab.groomersunite.entity.Role;
import com.petstylelab.groomersunite.entity.User;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class UserInfo {

    private final Long id;
    private final String loginId;
    private final String email;
    private final String password;
    private final String nickname;
    private final Role role;
    private final LocalDate registrationDate;

    public UserInfo(User user) {
        this.id = user.getId();
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.registrationDate = user.getRegistrationDate();
    }
}
