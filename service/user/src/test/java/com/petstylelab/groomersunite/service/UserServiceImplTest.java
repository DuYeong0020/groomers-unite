package com.petstylelab.groomersunite.service;

import com.petstylelab.groomersunite.entity.Role;
import com.petstylelab.groomersunite.entity.User;
import com.petstylelab.groomersunite.repository.UserRepository;
import com.petstylelab.groomersunite.service.request.UserCommand;
import com.petstylelab.groomersunite.service.response.UserInfo;
import com.petstylelab.groomersunite.validator.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void registerUser_성공() {
        String loginId = "loginId";
        String email = "email@email.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        String nickname = "nickname";
        Role role = Role.NORMAL;

        UserCommand.RegisterUserRequest request = new UserCommand.RegisterUserRequest(loginId, email, rawPassword, nickname);
        User user = new User(loginId, email, encodedPassword, nickname, role);

        given(passwordEncoder.encode(rawPassword)).willReturn(encodedPassword);
        given(userRepository.save(any(User.class))).willReturn(user);

        UserInfo result = userService.registerUser(request);

        assertThat(result.getLoginId()).isEqualTo(loginId);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(encodedPassword);
        assertThat(result.getNickname()).isEqualTo(nickname);
        assertThat(result.getRole()).isEqualTo(role);

        then(userValidator).should().checkRegisterUser(request);
        then(passwordEncoder).should().encode(rawPassword);
        then(userRepository).should().save(any(User.class));
    }

    @Test
    @DisplayName("회원 전체 수정")
    void modifyUser_전체수정() {
        String loginId = "loginId";
        String newEmail = "new@email.com";
        String newNickname = "newNickname";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";

        UserCommand.ModifyUserRequest request = new UserCommand.ModifyUserRequest(loginId, newEmail, newNickname, newPassword);
        User user = new User("loginId", "old@email.com", "oldPassword", "oldNickname", Role.NORMAL);

        given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));
        given(passwordEncoder.encode(newPassword)).willReturn(encodedPassword);

        UserInfo result = userService.modifyUser(request);

        assertThat(result.getEmail()).isEqualTo(newEmail);
        assertThat(result.getNickname()).isEqualTo(newNickname);
        assertThat(result.getPassword()).isEqualTo(encodedPassword);

        then(userValidator).should().checkModifyUser(request);
        then(passwordEncoder).should().encode(newPassword);
    }

    @Test
    @DisplayName("이메일만 수정")
    void modifyUser_이메일만수정() {
        String loginId = "loginId";
        String newEmail = "new@email.com";
        User user = new User("loginId", "old@email.com", "password", "nickname", Role.NORMAL);
        UserCommand.ModifyUserRequest request = new UserCommand.ModifyUserRequest(loginId, newEmail, null, null);

        given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));

        UserInfo result = userService.modifyUser(request);

        assertThat(result.getEmail()).isEqualTo(newEmail);
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());

        then(userValidator).should().checkModifyUser(request);
        then(passwordEncoder).should(never()).encode(any());
    }

    @Test
    @DisplayName("닉네임만 수정")
    void modifyUser_닉네임만수정() {
        String loginId = "loginId";
        String newNickname = "newNickname";
        User user = new User("loginId", "email@email.com", "password", "oldNickname", Role.NORMAL);
        UserCommand.ModifyUserRequest request = new UserCommand.ModifyUserRequest(loginId, null, newNickname, null);

        given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));

        UserInfo result = userService.modifyUser(request);

        assertThat(result.getNickname()).isEqualTo(newNickname);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());

        then(userValidator).should().checkModifyUser(request);
        then(passwordEncoder).should(never()).encode(any());
    }

    @Test
    @DisplayName("비밀번호만 수정")
    void modifyUser_비밀번호만수정() {
        String loginId = "loginId";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";
        User user = new User("loginId", "email@email.com", "oldPassword", "nickname", Role.NORMAL);
        UserCommand.ModifyUserRequest request = new UserCommand.ModifyUserRequest(loginId, null, null, newPassword);

        given(userRepository.findByLoginId(loginId)).willReturn(Optional.of(user));
        given(passwordEncoder.encode(newPassword)).willReturn(encodedPassword);

        UserInfo result = userService.modifyUser(request);

        assertThat(result.getPassword()).isEqualTo(encodedPassword);
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());

        then(userValidator).should().checkModifyUser(request);
        then(passwordEncoder).should().encode(newPassword);
    }

    @Test
    @DisplayName("비밀번호 수정 성공")
    void modifyPassword_성공() {
        Long userId = 2L;
        String newPassword = "newPassword";
        String encodedPassword = "encodedNewPassword";

        UserCommand.ModifyPasswordRequest request = new UserCommand.ModifyPasswordRequest(userId, newPassword);
        User user = new User("loginId", "email@email.com", "oldPassword", "nickname", Role.NORMAL);

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.encode(newPassword)).willReturn(encodedPassword);

        UserInfo result = userService.modifyPassword(request);

        assertThat(result.getPassword()).isEqualTo(encodedPassword);

        then(userValidator).should().checkModifyPassword(request);
        then(passwordEncoder).should().encode(newPassword);
    }

    @Test
    @DisplayName("비밀번호 수정 실패 - 유저 없음")
    void modifyPassword_유저없음_예외() {
        Long userId = 999L;
        String newPassword = "pw1234";
        UserCommand.ModifyPasswordRequest request = new UserCommand.ModifyPasswordRequest(userId, newPassword);

        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.modifyPassword(request));

        then(userValidator).should().checkModifyPassword(request);
        then(passwordEncoder).should(never()).encode(any());
    }

    @Test
    @DisplayName("이메일로 유저 조회 성공")
    void findUserByEmail_성공() {
        String email = "user@email.com";
        User user = new User("loginId", email, "password", "nickname", Role.NORMAL);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        UserInfo result = userService.findUserByEmail(email);

        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getNickname()).isEqualTo("nickname");
        assertThat(result.getPassword()).isEqualTo("password");
        assertThat(result.getRole()).isEqualTo(Role.NORMAL);

        then(userRepository).should().findByEmail(email);
    }

    @Test
    @DisplayName("이메일로 유저 조회 실패 - 유저 없음")
    void findUserByEmail_유저없음_예외() {
        String email = "notfound@email.com";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findUserByEmail(email));
    }
}
