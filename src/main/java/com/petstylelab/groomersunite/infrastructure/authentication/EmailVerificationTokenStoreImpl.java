package com.petstylelab.groomersunite.infrastructure.authentication;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationToken;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailVerificationTokenStoreImpl implements EmailVerificationTokenStore {

    private final EmailVerificationTokenJpaRepository emailVerificationTokenJpaRepository;

    @Override
    public EmailVerificationToken storeEmailVerificationToken(EmailVerificationToken emailVerificationToken) {
        return emailVerificationTokenJpaRepository.save(emailVerificationToken);
    }
}
