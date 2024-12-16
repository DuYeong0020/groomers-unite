package com.petstylelab.groomersunite.domain.user;

import com.petstylelab.groomersunite.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

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

    @Builder
    public User(String loginId, String email, String password, String nickname, Role role, LocalDate registrationDate) {
        if (!StringUtils.hasText(loginId)) throw new InvalidParameterException();
        if (!StringUtils.hasText(email)) throw new InvalidParameterException();
        if (!StringUtils.hasText(password)) throw new InvalidParameterException();
        if (!StringUtils.hasText(nickname)) throw new InvalidParameterException();
        if (role == null) throw new InvalidParameterException();
        if (registrationDate == null) throw new InvalidParameterException();

        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.registrationDate = registrationDate;
    }


    public void modifyNickname(String nickname) {
        if (StringUtils.hasText(nickname)) {
            this.nickname = nickname;
        }
    }

    public void modifyPassword(String password) {
        if (StringUtils.hasText(password)) {
            this.password = password;
        }
    }
}
