package com.petstylelab.groomersunite.controller.dto;

import com.petstylelab.groomersunite.entity.Role;
import com.petstylelab.groomersunite.service.request.UserCommand;
import com.petstylelab.groomersunite.service.response.UserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public class UserDto {

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

        @Email(message = "email 형식에 맞아야 합니다")
        private String email;

        @Size(min = 2, max = 8, message = "nickname은 최소 2글자, 최대 8글자 입니다.")
        private String nickname;

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String password;

        public UserCommand.ModifyUserRequest toCommand(String loginId) {
            return UserCommand.ModifyUserRequest.builder()
                    .email(email)
                    .nickname(nickname)
                    .password(password)
                    .loginId(loginId)
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

        @NotBlank(message = "userId는 필수값입니다.")
        private Long userId;

        @NotBlank(message = "confirmPassword는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String password;

        public UserCommand.ModifyPasswordRequest toCommand() {
            return UserCommand.ModifyPasswordRequest.builder()
                    .userId(userId)
                    .password(password)
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
