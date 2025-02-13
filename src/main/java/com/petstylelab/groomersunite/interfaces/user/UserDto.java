package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenInfo;
import com.petstylelab.groomersunite.domain.authentication.TokenType;
import com.petstylelab.groomersunite.domain.user.Role;
import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserInfo;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {

    @Getter
    @ToString
    public static class SendRegistrationEmailVerificationRequest {
        @NotBlank(message = "email은 필수값입니다")
        @Email(message = "email 형식에 맞아야 합니다")
        private String email;
    }

    @Getter
    @ToString
    public static class SendRegistrationEmailVerificationResponse {
        private final String token;

        private final String email;

        private final String body;

        private final LocalDateTime expiresAt;

        private final LocalDateTime confirmedAt;

        private final TokenType tokenType;

        public SendRegistrationEmailVerificationResponse(EmailVerificationTokenInfo emailVerificationTokenInfo) {
            this.token = emailVerificationTokenInfo.getToken();
            this.email = emailVerificationTokenInfo.getEmail();
            this.body = emailVerificationTokenInfo.getBody();
            this.expiresAt = emailVerificationTokenInfo.getExpiresAt();
            this.confirmedAt = emailVerificationTokenInfo.getConfirmedAt();
            this.tokenType = emailVerificationTokenInfo.getTokenType();
        }
    }

    @Getter
    @ToString
    public static class EmailTokenConfirmationRequest {
        @NotBlank(message = "email은 필수값입니다")
        @Email(message = "email 형식에 맞아야 합니다")
        private String email;

        @NotBlank(message = "token은 필수값입니다")
        private String token;
    }

    @Getter
    @ToString
    public static class SendAccountRecoveryVerificationRequest {
        @NotBlank(message = "email은 필수값입니다")
        @Email(message = "email 형식에 맞아야 합니다")
        private String email;
    }

    @Getter
    @ToString
    public static class SendAccountRecoveryVerificationResponse {
        private final String token;

        private final String email;

        private final String body;

        private final LocalDateTime expiresAt;

        private final LocalDateTime confirmedAt;

        private final TokenType tokenType;

        public SendAccountRecoveryVerificationResponse(EmailVerificationTokenInfo emailVerificationTokenInfo) {
            this.token = emailVerificationTokenInfo.getToken();
            this.email = emailVerificationTokenInfo.getEmail();
            this.body = emailVerificationTokenInfo.getBody();
            this.expiresAt = emailVerificationTokenInfo.getExpiresAt();
            this.confirmedAt = emailVerificationTokenInfo.getConfirmedAt();
            this.tokenType = emailVerificationTokenInfo.getTokenType();
        }
    }

    @Getter
    @ToString
    public static class RegisterRequest {
        @NotBlank(message = "loginId는 필수값입니다.")
        @Size(min = 5, max = 15, message = "loginId는 최소 5자, 최대 15자이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "loginId는 영문자와 숫자만 허용됩니다.")
        private String loginId;

        @NotBlank(message = "email은 필수값입니다")
        @Email(message = "email 형식에 맞아야 합니다")
        private String email;

        @NotBlank(message = "password는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String password;

        @NotBlank(message = "nickname은 필수값입니다")
        @Size(min = 2, max = 8, message = "nickname은 최소 2글자, 최대 8글자 입니다.")
        private String nickname;

        public UserCommand.RegisterUserRequest toCommand() {
            return UserCommand.RegisterUserRequest.builder()
                    .loginId(loginId)
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class RegisterResponse {
        private final String loginId;
        private final String email;
        private final String nickname;
        private final String password;
        private final Role role;
        private final LocalDate registrationDate;


        public RegisterResponse(UserInfo userInfo) {
            this.loginId = userInfo.getLoginId();
            this.email = userInfo.getEmail();
            this.nickname = userInfo.getNickname();
            this.password = userInfo.getPassword();
            this.role = userInfo.getRole();
            this.registrationDate = userInfo.getRegistrationDate();
        }
    }

    @Getter
    @ToString
    public static class ModifyUserRequest {

        @NotBlank(message = "nickname은 필수값입니다")
        @Size(min = 2, max = 8, message = "nickname은 최소 2글자, 최대 8글자 입니다.")
        private String nickname;

        @NotBlank(message = "currentPassword는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String currentPassword;

        @NotBlank(message = "newPassword는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String newPassword;

        public UserCommand.ModifyUserRequest toCommand(Long userId) {
            return UserCommand.ModifyUserRequest.builder()
                    .nickname(nickname)
                    .currentPassword(currentPassword)
                    .newPassword(newPassword)
                    .build();

        }
    }

    @Getter
    @ToString
    public static class ModifyUserResponse {
        private final String loginId;
        private final String email;
        private final String nickname;
        private final String password;
        private final Role role;
        private final LocalDate registrationDate;

        public ModifyUserResponse(UserInfo userInfo) {
            this.loginId = userInfo.getLoginId();
            this.email = userInfo.getEmail();
            this.nickname = userInfo.getNickname();
            this.password = userInfo.getPassword();
            this.role = userInfo.getRole();
            this.registrationDate = userInfo.getRegistrationDate();
        }
    }

    @Getter
    @ToString
    public static class ModifyPasswordRequest {
        @NotBlank(message = "email은 필수값입니다")
        @Email(message = "email 형식에 맞아야 합니다")
        private String email;

        @NotBlank(message = "newPassword는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String newPassword;

        @NotBlank(message = "confirmPassword는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String confirmPassword;

        public UserCommand.ModifyPasswordRequest toCommand() {
            return UserCommand.ModifyPasswordRequest.builder()
                    .email(email)
                    .newPassword(newPassword)
                    .confirmPassword(confirmPassword)
                    .build();
        }
    }


    @Getter
    @ToString
    public static class ModifyPasswordResponse {
        private final String loginId;
        private final String email;
        private final String nickname;
        private final String password;
        private final Role role;
        private final LocalDate registrationDate;

        public ModifyPasswordResponse(UserInfo userInfo) {
            this.loginId = userInfo.getLoginId();
            this.email = userInfo.getEmail();
            this.nickname = userInfo.getNickname();
            this.password = userInfo.getPassword();
            this.role = userInfo.getRole();
            this.registrationDate = userInfo.getRegistrationDate();
        }
    }

    @Getter
    @ToString
    public static class FindLoginIdRequest {

        @NotBlank(message = "email은 필수값입니다")
        @Email(message = "email 형식에 맞아야 합니다")
        private String email;

    }

    @Getter
    @ToString
    public static class FindLoginIdResponse {
        private final String loginId;
        private final String email;
        private final String nickname;
        private final String password;
        private final Role role;
        private final LocalDate registrationDate;

        public FindLoginIdResponse(UserInfo userInfo) {
            this.loginId = userInfo.getLoginId();
            this.email = userInfo.getEmail();
            this.nickname = userInfo.getNickname();
            this.password = userInfo.getPassword();
            this.role = userInfo.getRole();
            this.registrationDate = userInfo.getRegistrationDate();
        }
    }

}
