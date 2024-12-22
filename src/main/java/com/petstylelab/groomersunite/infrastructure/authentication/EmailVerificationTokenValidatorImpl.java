package com.petstylelab.groomersunite.infrastructure.authentication;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenValidator;
import com.petstylelab.groomersunite.infrastructure.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerificationTokenValidatorImpl implements EmailVerificationTokenValidator {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void checkSendRegistrationVerificationEmail(String email) {
        checkDuplicateEmail(email);
    }

    private void checkDuplicateEmail(String email) {
        userJpaRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 등록된 이메일 주소입니다.");
                });
    }
}
