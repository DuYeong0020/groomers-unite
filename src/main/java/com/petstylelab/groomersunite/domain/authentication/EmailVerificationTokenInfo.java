package com.petstylelab.groomersunite.domain.authentication;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailVerificationTokenInfo {
    private final String token;

    private final String email;

    private final String body;

    private final LocalDateTime expiresAt;

    private final LocalDateTime confirmedAt;

    private final TokenType tokenType;

    public EmailVerificationTokenInfo(EmailVerificationToken emailVerificationToken) {
        this.token = emailVerificationToken.getToken();
        this.email = emailVerificationToken.getEmail();
        this.body = emailVerificationToken.getBody();
        this.expiresAt = emailVerificationToken.getExpiresAt();
        this.confirmedAt = emailVerificationToken.getConfirmedAt();
        this.tokenType = emailVerificationToken.getTokenType();
    }
}
