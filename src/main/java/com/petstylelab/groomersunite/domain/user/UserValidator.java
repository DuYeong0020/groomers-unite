package com.petstylelab.groomersunite.domain.user;

public interface UserValidator {

    void checkRegisterUser(UserCommand.RegisterUserRequest request);
}
