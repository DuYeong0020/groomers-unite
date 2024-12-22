package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenService {

    EmailVerificationTokenInfo sendRegistrationVerificationEmail(String email);
}
