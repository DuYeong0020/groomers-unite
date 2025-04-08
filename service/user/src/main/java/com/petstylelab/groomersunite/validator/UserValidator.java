package com.petstylelab.groomersunite.validator;

import com.petstylelab.groomersunite.service.request.UserCommand;

public interface UserValidator {

    void checkRegisterUser(UserCommand.RegisterUserRequest request);

    void checkModifyUser(UserCommand.ModifyUserRequest request);

    void checkModifyPassword(UserCommand.ModifyPasswordRequest request);
}
