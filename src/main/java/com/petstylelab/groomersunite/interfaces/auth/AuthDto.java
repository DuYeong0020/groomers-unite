package com.petstylelab.groomersunite.interfaces.auth;

import com.petstylelab.groomersunite.domain.user.UserCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class AuthDto {

    @Getter
    @Setter
    @ToString
    public static class LoginRequest {

        @NotBlank(message = "loginId는 필수값입니다.")
        @Size(min = 5, max = 15, message = "loginId는 최소 5자, 최대 15자이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "loginId는 영문자와 숫자만 허용됩니다.")
        private String loginId;

        @NotBlank(message = "password는 필수값입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 '숫자', '문자', '특수문자'를 최소 1개 이상 포함하며, 8자에서 16자까지 허용됩니다.")
        private String password;

        public UserCommand.AuthenticateUserRequest toCommand() {
            return UserCommand.AuthenticateUserRequest.builder()
                    .loginId(loginId)
                    .password(password)
                    .build();
        }
    }
}
