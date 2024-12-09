package com.petstylelab.groomersunite.infrastructure.user;

import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void checkRegisterUser(UserCommand.RegisterUserRequest request) {
        checkDuplicateLoginId(request.getLoginId());
        checkDuplicateEmail(request.getEmail());
        checkDuplicateNickname(request.getNickname());
    }



    private void checkDuplicateNickname(String nickname) {
        userJpaRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 nickname 입니다.");
                });
    }

    private void checkDuplicateEmail(String email) {
        userJpaRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 email 입니다.");
                });

    }

    private void checkDuplicateLoginId(String loginId) {
        userJpaRepository.findByLoginId(loginId)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 loginId 입니다.");
                });
    }
}
