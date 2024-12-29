package com.petstylelab.groomersunite.infrastructure.authentication;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationToken;
import com.petstylelab.groomersunite.domain.authentication.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenJpaRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByEmailAndTokenAndTokenType(String email, String token, TokenType tokenType);
}
