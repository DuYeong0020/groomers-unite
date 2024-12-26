package com.petstylelab.groomersunite.domain.authentication;

public interface EmailVerificationTokenValidator {

    void checkSendRegistrationVerificationEmail(String email);

    void checkSendRecoveryVerificationEmail(String email);

}
