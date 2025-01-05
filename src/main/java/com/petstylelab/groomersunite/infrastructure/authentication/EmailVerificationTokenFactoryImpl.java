package com.petstylelab.groomersunite.infrastructure.authentication;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationToken;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenFactory;
import com.petstylelab.groomersunite.domain.authentication.TokenType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EmailVerificationTokenFactoryImpl implements EmailVerificationTokenFactory {
    private static final String VERIFICATION_EMAIL_SUBJECT = "Verification email";
    private static final String VERIFICATION_EMAIL_BODY = "Your verification code is: ";
    private static final long TOKEN_EXPIRATION_MINUTES = 5;

    public EmailVerificationToken createVerificationToken(String email, TokenType tokenType) {
        String token = UUID.randomUUID().toString();
        String body = VERIFICATION_EMAIL_BODY + token;
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES);

        return EmailVerificationToken.builder()
                .email(email)
                .subject(VERIFICATION_EMAIL_SUBJECT)
                .body(body)
                .expiresAt(expiresAt)
                .token(token)
                .tokenType(tokenType)
                .build();

    }
}
