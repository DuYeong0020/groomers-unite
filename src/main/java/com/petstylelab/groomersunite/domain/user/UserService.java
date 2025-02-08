package com.petstylelab.groomersunite.domain.user;

public interface UserService {
    // 회원 가입
    UserInfo registerUser(UserCommand.RegisterUserRequest request);
    // 회원 수정
    UserInfo modifyUser(UserCommand.ModifyUserRequest request);

    // 비밀번호 수정
    UserInfo modifyPassword(UserCommand.ModifyPasswordRequest request);

    UserInfo findUserByEmail(String email);

    UserInfo authenticateUser(UserCommand.AuthenticateUserRequest request);

}