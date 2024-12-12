package com.petstylelab.groomersunite.infrastructure.user;

import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserReader;
import com.petstylelab.groomersunite.domain.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserJpaRepository userJpaRepository;
    private final UserReader userReader;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkRegisterUser(UserCommand.RegisterUserRequest request) {
        checkDuplicateLoginId(request.getLoginId());
        checkDuplicateEmail(request.getEmail());
        checkDuplicateNickname(request.getNickname());
    }

    @Override
    public void checkModifyUser(UserCommand.ModifyUserRequest request) {
        checkMatchLoginIdPassword(request.getLoginId(), request.getCurrentPassword());
        checkPasswordNotSame(request.getCurrentPassword(), request.getNewPassword());
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

    private void checkPasswordNotSame(String currentPassword, String newPassword) {
        if (currentPassword.equals(newPassword)) {
            throw new RuntimeException("현재 비밀번호와 새 비밀번호는 동일할 수 없습니다.");
        }
    }

    private void checkMatchLoginIdPassword(String loginId, String password) {
        var user = userReader.findByLoginId(loginId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
