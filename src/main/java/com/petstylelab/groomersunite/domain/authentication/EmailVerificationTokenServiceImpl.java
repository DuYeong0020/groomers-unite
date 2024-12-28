package com.petstylelab.groomersunite.domain.authentication;

import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenValidator emailVerificationTokenValidator;
    private final EmailVerificationTokenFactory emailVerificationTokenFactory;
    private final EmailVerificationTokenStore emailVerificationTokenStore;
    private final EmailVerificationTokenReader emailVerificationTokenReader;
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
    @Transactional
    public EmailVerificationTokenInfo sendRecoveryVerificationEmail(String email) {
        emailVerificationTokenValidator.checkSendRecoveryVerificationEmail(email);
        TokenType tokenType = TokenType.ACCOUNT_RECOVERY;
        EmailVerificationToken initVerificationToken = emailVerificationTokenFactory.createVerificationToken(email, tokenType);
        User user = userReader.findByEmail(email);
        EmailVerificationToken emailVerificationToken = emailVerificationTokenStore.storeEmailVerificationToken(initVerificationToken);
        emailVerificationToken.assignUser(user);
        emailSender.sendEmail(email, emailVerificationToken.getSubject(), emailVerificationToken.getBody());
        return new EmailVerificationTokenInfo(emailVerificationToken);
    }


    @Override
    public boolean verifyRegistrationToken(String email, String token) {
        emailVerificationTokenValidator.checkVerifyRegistrationToken(email, token);
        EmailVerificationToken emailVerificationToken =
                emailVerificationTokenReader.findByEmailAndTokenAndTokenType(email, token, TokenType.EMAIL_VERIFICATION);
        emailVerificationToken.modifyConfirmedAt(LocalDateTime.now());
        return true;
    }

    @Override
    public boolean verifyRecoveryToken(String email, String token) {
        return false;
    }
}