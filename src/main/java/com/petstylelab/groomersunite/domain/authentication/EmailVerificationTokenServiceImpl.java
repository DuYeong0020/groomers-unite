package com.petstylelab.groomersunite.domain.authentication;

import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenValidator emailVerificationTokenValidator;
    private final EmailVerificationTokenFactory emailVerificationTokenFactory;
    private final EmailVerificationTokenStore emailVerificationTokenStore;
    private final UserReader userReader;
    private final EmailSender emailSender;


    @Override
    @Transactional
    public EmailVerificationTokenInfo sendRegistrationVerificationEmail(String email) {
        emailVerificationTokenValidator.checkSendRegistrationVerificationEmail(email);
        TokenType tokenType = TokenType.EMAIL_VERIFICATION;
        EmailVerificationToken initVerificationToken = emailVerificationTokenFactory.createVerificationToken(email, tokenType);
        EmailVerificationToken emailVerificationToken = emailVerificationTokenStore.storeEmailVerificationToken(initVerificationToken);
        emailSender.sendEmail(email, emailVerificationToken.getSubject(), emailVerificationToken.getBody());
        return new EmailVerificationTokenInfo(emailVerificationToken);
    }

    @Override
    public EmailVerificationTokenInfo sendRecoveryVerificationEmail(String email) {
        emailVerificationTokenValidator.checkSendRecoveryVerificationEmail(email);
        TokenType tokenType = TokenType.EMAIL_VERIFICATION;
        EmailVerificationToken initVerificationToken = emailVerificationTokenFactory.createVerificationToken(email, tokenType);
        User user = userReader.findByEmail(email);
        EmailVerificationToken emailVerificationToken = emailVerificationTokenStore.storeEmailVerificationToken(initVerificationToken);
        emailVerificationToken.assignUser(user);
        emailSender.sendEmail(email, emailVerificationToken.getSubject(), emailVerificationToken.getBody());
        return new EmailVerificationTokenInfo(emailVerificationToken);
    }
}