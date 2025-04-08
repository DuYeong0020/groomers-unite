package com.petstylelab.groomersunite.validator;

import com.petstylelab.groomersunite.entity.User;
import com.petstylelab.groomersunite.repository.UserRepository;
import com.petstylelab.groomersunite.service.request.UserCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserValidatorImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserValidatorImpl userValidator;

    @Test
    void checkRegisterUser_중복_없으면_예외없음() {
        // given
        UserCommand.RegisterUserRequest request = new UserCommand.RegisterUserRequest("loginId", "email@email.com", "password", "nickname");

        given(userRepository.findByLoginId(request.getLoginId())).willReturn(Optional.empty());
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByNickname(request.getNickname())).willReturn(Optional.empty());

        // when, then
        assertDoesNotThrow(() -> userValidator.checkRegisterUser(request));

        then(userRepository).should().findByLoginId(request.getLoginId());
        then(userRepository).should().findByEmail(request.getEmail());
        then(userRepository).should().findByNickname(request.getNickname());
    }

    @Test
    void checkRegisterUser_중복_loginId_있으면_예외() {
        // given
        UserCommand.RegisterUserRequest request = new UserCommand.RegisterUserRequest("loginId", "email@email.com", "password", "nickname");
        given(userRepository.findByLoginId(request.getLoginId())).willReturn(Optional.of(mock(User.class)));

        // when, then
        assertThrows(RuntimeException.class, () -> userValidator.checkRegisterUser(request));

        then(userRepository).should().findByLoginId(request.getLoginId());
        then(userRepository).should(never()).findByEmail(request.getEmail());
        then(userRepository).should(never()).findByNickname(request.getNickname());
    }

    @Test
    void checkRegisterUser_중복_email_있으면_예외() {
        UserCommand.RegisterUserRequest request = new UserCommand.RegisterUserRequest("loginId", "email@email.com", "password", "nickname");
        given(userRepository.findByLoginId(request.getLoginId())).willReturn(Optional.empty());
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(mock(User.class)));

        // when, then
        assertThrows(RuntimeException.class, () -> userValidator.checkRegisterUser(request));

        then(userRepository).should().findByLoginId(request.getLoginId());
        then(userRepository).should().findByEmail(request.getEmail());
        then(userRepository).should(never()).findByNickname(request.getNickname());
    }

    @Test
    void checkRegisterUser_중복_nickname_있으면_예외() {
        UserCommand.RegisterUserRequest request = new UserCommand.RegisterUserRequest("loginId", "email@email.com", "password", "nickname");
        given(userRepository.findByLoginId(request.getLoginId())).willReturn(Optional.empty());
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByNickname(request.getNickname())).willReturn(Optional.of(mock(User.class)));

        assertThrows(RuntimeException.class, () -> userValidator.checkRegisterUser(request));
        then(userRepository).should().findByLoginId(request.getLoginId());
        then(userRepository).should().findByEmail(request.getEmail());
        then(userRepository).should().findByNickname(request.getNickname());
    }

    @Test
    void checkModifyUser_중복없고_비밀번호_다르면_예외없음() {
        UserCommand.ModifyUserRequest request = new UserCommand.ModifyUserRequest("loginId", "email", "nickname", "password");
        String encodedPassword = "encodedPassword";

        var user = mock(User.class);
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByNickname(request.getNickname())).willReturn(Optional.empty());
        given(userRepository.findByLoginId(request.getLoginId())).willReturn(Optional.of(user));
        given(user.getPassword()).willReturn(encodedPassword);
        given(passwordEncoder.matches(request.getPassword(), encodedPassword)).willReturn(false);

        assertDoesNotThrow(() -> userValidator.checkModifyUser(request));
    }

    @Test
    void checkModifyUser_기존과_동일한_비밀번호면_예외() {
        var request = new UserCommand.ModifyUserRequest("loginId", "email", "nickname", "password");
        String encodedPassword = "encodedPassword";

        var user = mock(User.class);
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());
        given(userRepository.findByNickname(request.getNickname())).willReturn(Optional.empty());
        given(userRepository.findByLoginId(request.getLoginId())).willReturn(Optional.of(user));
        given(user.getPassword()).willReturn(encodedPassword);
        given(passwordEncoder.matches(request.getPassword(), encodedPassword)).willReturn(true);

        assertThrows(RuntimeException.class, () -> userValidator.checkModifyUser(request));
    }

    @Test
    void checkModifyPassword_비밀번호_다르면_예외없음() {
        var request = new UserCommand.ModifyPasswordRequest(1L, "password");
        String encodedPassword = "encodedPassword";

        var user = mock(User.class);
        given(userRepository.findById(request.getUserId())).willReturn(Optional.of(user));
        given(user.getPassword()).willReturn(encodedPassword);
        given(passwordEncoder.matches(request.getPassword(), encodedPassword)).willReturn(false);

        assertDoesNotThrow(() -> userValidator.checkModifyPassword(request));
    }

    @Test
    void checkModifyPassword_기존과_동일하면_예외() {
        var request = new UserCommand.ModifyPasswordRequest(1L, "password");
        String encodedPassword = "encodedPassword";

        var user = mock(User.class);
        given(userRepository.findById(request.getUserId())).willReturn(Optional.of(user));
        given(user.getPassword()).willReturn(encodedPassword);
        given(passwordEncoder.matches(request.getPassword(), encodedPassword)).willReturn(true);

        assertThrows(RuntimeException.class, () -> userValidator.checkModifyPassword(request));
    }
}
