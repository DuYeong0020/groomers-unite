package com.petstylelab.groomersunite.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfo registerUser(UserCommand.RegisterUserRequest request) {
        userValidator.checkRegisterUser(request);
        var encodedPassword = passwordEncoder.encode(request.getPassword());
        var initUser = request.toEntity(encodedPassword, Role.NORMAL, LocalDate.now());
        var user = userStore.storeUser(initUser);
        return new UserInfo(user);
    }

    @Override
    public UserInfo modifyUser(UserCommand.ModifyUserRequest request) {
        return null;
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
