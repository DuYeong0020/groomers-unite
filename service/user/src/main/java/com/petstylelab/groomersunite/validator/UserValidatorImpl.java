package com.petstylelab.groomersunite.validator;

import com.petstylelab.groomersunite.repository.UserRepository;
import com.petstylelab.groomersunite.service.request.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkRegisterUser(UserCommand.RegisterUserRequest request) {
        checkDuplicateLoginId(request.getLoginId());
        checkDuplicateEmail(request.getEmail());
        checkDuplicateNickname(request.getNickname());
    }

    @Override
    public void checkModifyUser(UserCommand.ModifyUserRequest request) {
        checkDuplicateEmail(request.getEmail());
        checkDuplicateNickname(request.getNickname());
        checkPasswordNotSameAsBeforeByLoginId(request.getLoginId(), request.getPassword());

    }



    @Override
    public void checkModifyPassword(UserCommand.ModifyPasswordRequest request) {
        checkPasswordNotSameAsBeforeByUserId(request.getUserId(), request.getPassword());
    }



    private void checkDuplicateNickname(String nickname) {
        userRepository.findByNickname(nickname)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 nickname 입니다.");
                });
    }

    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 email 입니다.");
                });

    }

    private void checkDuplicateLoginId(String loginId) {
        userRepository.findByLoginId(loginId)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 loginId 입니다.");
                });
    }
    private void checkPasswordNotSameAsBeforeByUserId(Long userId, String password) {
        userRepository.findById(userId)
                .filter(user -> !passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new RuntimeException("기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다."));;
    }
    private void checkPasswordNotSameAsBeforeByLoginId(String loginId, String password) {
        userRepository.findByLoginId(loginId)
                .filter(user -> !passwordEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new RuntimeException("기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다."));;
    }
}
