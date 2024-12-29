package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenValidator {

    void checkSendRegistrationVerificationEmail(String email);

    void checkSendRecoveryVerificationEmail(String email);

    void checkVerifyRegistrationToken(String email, String token);

    void checkVerifyRecoveryToken(String email, String token);
}
