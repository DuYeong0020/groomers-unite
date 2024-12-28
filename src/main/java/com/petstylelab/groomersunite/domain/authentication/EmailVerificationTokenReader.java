package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenReader {

    EmailVerificationToken findByEmailAndTokenAndTokenType(String email, String token, TokenType tokenType);
}
