package com.petstylelab.groomersunite.infrastructure.authentication;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationToken;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenReader;
import com.petstylelab.groomersunite.domain.authentication.TokenType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerificationTokenJpaReader implements EmailVerificationTokenReader {

    private final EmailVerificationTokenJpaRepository emailVerificationTokenJpaRepository;

    @Override
    public EmailVerificationToken findByEmailAndTokenAndTokenType(String email, String token, TokenType tokenType) {
        return emailVerificationTokenJpaRepository.findByEmailAndTokenAndTokenType(email, token, tokenType)
                .orElseThrow(EntityNotFoundException::new);
    }
}
