package com.petstylelab.groomersunite.service;

import com.petstylelab.groomersunite.service.request.UserCommand;
import com.petstylelab.groomersunite.service.response.UserInfo;

public interface UserService {

    UserInfo registerUser(UserCommand.RegisterUserRequest request);

    UserInfo modifyUser(UserCommand.ModifyUserRequest request);

    UserInfo modifyPassword(UserCommand.ModifyPasswordRequest request);

    UserInfo findUserByEmail(String email);
}
