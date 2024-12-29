package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenInfo;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenService;
import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserCriteria;
import com.petstylelab.groomersunite.domain.user.UserInfo;
import com.petstylelab.groomersunite.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/users/email-verification/confirm")
    public CommonResponse<Boolean> confirmRegistrationEmailToken(@RequestBody @Valid UserDto.EmailTokenConfirmationRequest request) {
        boolean response = emailVerificationTokenService.verifyRegistrationToken(request.getEmail(), request.getToken());
        return CommonResponse.success(response);
    }

    @PostMapping("/users/recovery-verification/confirm")
    public CommonResponse<Boolean> confirmAccountRecoveryEmailToken(@RequestBody @Valid UserDto.EmailTokenConfirmationRequest request) {
        boolean response = emailVerificationTokenService.verifyRecoveryToken(request.getEmail(), request.getToken());
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

    @PatchMapping("/users/password")
    public CommonResponse<UserDto.ModifyPasswordResponse> modifyPassword(@RequestBody @Valid UserDto.ModifyPasswordRequest request) {
        UserCommand.ModifyPasswordRequest command = request.toCommand();
        UserInfo userInfo = userService.modifyPassword(command);
        UserDto.ModifyPasswordResponse response = new UserDto.ModifyPasswordResponse(userInfo);
        return CommonResponse.success(response);
    }


    @GetMapping("/users/login-id")
    public CommonResponse<UserDto.FindLoginIdResponse> findLoginId(@RequestBody @Valid UserDto.FindLoginIdRequest request) {
        UserCriteria.FindUserCriteria criteria = request.toCriteria();
        UserInfo userInfo = userService.findUserId(criteria);
        UserDto.FindLoginIdResponse response = new UserDto.FindLoginIdResponse(userInfo);
        return CommonResponse.success(response);
    }
}
