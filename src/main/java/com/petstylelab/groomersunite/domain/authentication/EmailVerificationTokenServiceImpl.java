package com.petstylelab.groomersunite.domain.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenValidator emailVerificationTokenValidator;
    private final EmailVerificationTokenFactory emailVerificationTokenFactory;
    private final EmailVerificationTokenStore emailVerificationTokenStore;

    @Override
    @Transactional
    public EmailVerificationTokenInfo sendRegistrationVerificationEmail(String email) {
        emailVerificationTokenValidator.checkSendRegistrationVerificationEmail(email);
        TokenType tokenType = TokenType.EMAIL_VERIFICATION;
        EmailVerificationToken initVerificationToken = emailVerificationTokenFactory.createVerificationToken(email, tokenType);
        EmailVerificationToken emailVerificationToken = emailVerificationTokenStore.storeEmailVerificationToken(initVerificationToken);
        return null;
    }
}