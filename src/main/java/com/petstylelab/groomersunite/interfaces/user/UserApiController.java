package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenInfo;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenService;
import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserInfo;
import com.petstylelab.groomersunite.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    @PostMapping("/users/email-verification")
    public CommonResponse<UserDto.RegistrationEmailVerificationResponse> sendRegistrationEmailVerification(@RequestBody @Valid UserDto.RegistrationEmailVerificationRequest request) {
        EmailVerificationTokenInfo emailVerificationTokenInfo = emailVerificationTokenService.sendRegistrationVerificationEmail(request.getEmail());
        UserDto.RegistrationEmailVerificationResponse response = new UserDto.RegistrationEmailVerificationResponse(emailVerificationTokenInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/users/recovery-verification")
    public CommonResponse<UserDto.AccountRecoveryVerificationResponse> sendAccountRecoveryEmailVerification(@RequestBody @Valid UserDto.AccountRecoveryVerificationRequest request) {
        EmailVerificationTokenInfo emailVerificationTokenInfo = emailVerificationTokenService.sendRecoveryVerificationEmail(request.getEmail());
        UserDto.AccountRecoveryVerificationResponse response = new UserDto.AccountRecoveryVerificationResponse(emailVerificationTokenInfo);
        return CommonResponse.success(response);
    }


    @PostMapping("/users")
    public CommonResponse<UserDto.RegisterResponse> registerUser(@RequestBody @Valid UserDto.RegisterRequest request) {
        UserCommand.RegisterUserRequest command = request.toCommand();
        UserInfo userInfo = userService.registerUser(command);
        UserDto.RegisterResponse response = new UserDto.RegisterResponse(userInfo);

        return CommonResponse.success(response);
    }

    @PatchMapping("/users")
    public CommonResponse<UserDto.ModifyUserResponse> modifyUser(@RequestBody @Valid UserDto.ModifyUserRequest request) {
        UserCommand.ModifyUserRequest command = request.toCommand();
        UserInfo userInfo = userService.modifyUser(command);
        UserDto.ModifyUserResponse response = new UserDto.ModifyUserResponse(userInfo);

        return CommonResponse.success(response);
    }
}
