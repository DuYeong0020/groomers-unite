package com.petstylelab.groomersunite.domain.user;

public interface UserValidator {

    void checkRegisterUser(UserCommand.RegisterUserRequest request);

    void checkModifyUser(UserCommand.ModifyUserRequest request);

    void checkModifyPassword(UserCommand.ModifyPasswordRequest request);

    void checkAuthenticateUser(UserCommand.AuthenticateUserRequest request);
}
