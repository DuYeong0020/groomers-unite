package com.petstylelab.groomersunite.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserInfo registerUser(UserCommand.RegisterUserRequest request) {
        return null;
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
