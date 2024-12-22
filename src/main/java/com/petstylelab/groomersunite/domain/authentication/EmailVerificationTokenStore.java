package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenStore {

    EmailVerificationToken storeEmailVerificationToken(EmailVerificationToken emailVerificationToken);
}
