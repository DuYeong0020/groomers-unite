package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenFactory {
    EmailVerificationToken createVerificationToken(String email, TokenType tokenType);
}
