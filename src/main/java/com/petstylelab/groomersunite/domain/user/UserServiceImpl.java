package com.petstylelab.groomersunite.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;
    private final UserReader userReader;

    @Override
    @Transactional
    public UserInfo registerUser(UserCommand.RegisterUserRequest request) {
        userValidator.checkRegisterUser(request);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User initUser = request.toEntity(encodedPassword, Role.NORMAL, LocalDate.now());
        User user = userStore.storeUser(initUser);
        return new UserInfo(user);
    }

    @Override
    @Transactional
    public UserInfo modifyUser(UserCommand.ModifyUserRequest request) {
        userValidator.checkModifyUser(request);
        User user = userReader.findByLoginId(request.getLoginId());
        String encodedPassword = passwordEncoder.encode(request.getCurrentPassword());
        user.modifyNickname(request.getNickname());
        user.modifyPassword(encodedPassword);
        return new UserInfo(user);
    }

    @Override
    public UserInfo modifyPassword(UserCommand.ModifyPasswordRequest request) {
        return null;
    }

    @Override
    public UserInfo findLoginIdByEmail(String email) {
        return null;
    }

    @Override
    public boolean sendVerificationCodeForRegister(String email) {
        return false;
    }

    @Override
    public boolean sendVerificationCodeForFindAccount(String email) {
        return false;
    }

    @Override
    public boolean confirmVerificationCode(String verificationCode) {
        return false;
    }
}
