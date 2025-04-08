package com.petstylelab.groomersunite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String refreshToken;
    private String expiration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RefreshToken(String loginId, String refreshToken, String expiration) {
        if (!StringUtils.hasText(loginId)) throw new InvalidParameterException();
        if (!StringUtils.hasText(refreshToken)) throw new InvalidParameterException();
        if (!StringUtils.hasText(expiration)) throw new InvalidParameterException();

        this.loginId = loginId;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }
}
