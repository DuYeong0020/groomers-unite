package com.petstylelab.groomersunite.domain.user;

public interface UserService {
    // 회원 가입
    UserInfo registerUser(UserCommand.RegisterUserRequest request);
    // 회원 수정
    UserInfo modifyUser(UserCommand.ModifyUserRequest request);

    // 비밀번호 수정
    UserInfo modifyPassword(UserCommand.ModifyPasswordRequest request);

    // 이메일로 유저 아이디 찾기
    UserInfo findLoginIdByEmail(String email);

    // 회원가입을 위한 인증 번호 보내기
    boolean sendVerificationCodeForRegister(String email);

    // 계정찾기를 위한 인증 번호 보내기
    boolean sendVerificationCodeForFindAccount(String email);

    // 인증 번호 확인하기
    boolean confirmVerificationCode(String verificationCode);

}