package com.petstylelab.groomersunite.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String email;
    private String password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDate registrationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public User(String loginId, Role role) {
        if (!StringUtils.hasText(loginId)) throw new InvalidParameterException();
        if (role == null) throw new InvalidParameterException();

        this.loginId = loginId;
        this.role = role;
    }

    @Builder
    public User(String loginId, String email, String password, String nickname, Role role) {
        if (!StringUtils.hasText(loginId)) throw new InvalidParameterException();
        if (!StringUtils.hasText(email)) throw new InvalidParameterException();
        if (!StringUtils.hasText(password)) throw new InvalidParameterException();
        if (!StringUtils.hasText(nickname)) throw new InvalidParameterException();
        if (role == null) throw new InvalidParameterException();

        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.registrationDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public void modifyPassword(String password) {
        if (StringUtils.hasText(password)) {
            this.password = password;
        }
    }

    public void modifyNickname(String nickname) {
        if (StringUtils.hasText(nickname)) {
            this.nickname = nickname;
        }
    }

    public void modifyEmail(String email) {
        if (StringUtils.hasText(email)) {
            this.email = email;
        }
    }

}