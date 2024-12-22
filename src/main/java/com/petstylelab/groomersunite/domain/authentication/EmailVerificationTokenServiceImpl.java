package com.petstylelab.groomersunite.domain.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    @Override
    @Transactional
    public EmailVerificationTokenInfo sendRegistrationVerificationEmail(String email) {
        return null;
    }
}