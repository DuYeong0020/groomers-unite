package com.petstylelab.groomersunite.infrastructure.authentication;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationToken;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenReader;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenValidator;
import com.petstylelab.groomersunite.domain.authentication.TokenType;
import com.petstylelab.groomersunite.infrastructure.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerificationTokenValidatorImpl implements EmailVerificationTokenValidator {

    private final EmailVerificationTokenReader emailVerificationTokenReader;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void checkSendRegistrationVerificationEmail(String email) {
        checkDuplicateEmail(email);
    }

    @Override
    public void checkSendRecoveryVerificationEmail(String email) {
        checkEmailExistsForRecovery(email);
    }

    @Override
    public void checkVerifyRegistrationToken(String email, String token) {
        EmailVerificationToken emailVerificationToken = checkMatchEmailTokenTokenType(email, token, TokenType.EMAIL_VERIFICATION);
        checkTokenNotExpired(emailVerificationToken);
    }

    private EmailVerificationToken checkMatchEmailTokenTokenType(String email, String token, TokenType tokenType) {
        return emailVerificationTokenReader
                .findByEmailAndTokenAndTokenType(email, token, tokenType);
    }

    private void checkTokenNotExpired(EmailVerificationToken token) {
        if (token.isExpired()) {
            throw new RuntimeException("이미 만료된 시간입니다.");
        }
    }

    private void checkEmailExistsForRecovery(String email) {
        userJpaRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일로 등록된 사용자가 없습니다."));
    }

    private void checkDuplicateEmail(String email) {
        userJpaRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("이미 등록된 이메일 주소입니다.");
        });
    }
}
