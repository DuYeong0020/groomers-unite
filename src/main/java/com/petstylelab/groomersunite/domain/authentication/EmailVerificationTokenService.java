package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenService {

    EmailVerificationTokenInfo sendRecoveryVerificationEmail(String email);

    EmailVerificationTokenInfo sendRegistrationVerificationEmail(String email);
}
