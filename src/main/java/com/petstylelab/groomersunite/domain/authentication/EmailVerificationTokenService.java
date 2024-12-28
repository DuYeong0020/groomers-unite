package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenService {

    EmailVerificationTokenInfo sendRecoveryVerificationEmail(String email);

    EmailVerificationTokenInfo sendRegistrationVerificationEmail(String email);

    boolean verifyRegistrationToken(String email, String token);

    boolean verifyRecoveryToken(String email, String token);
}
