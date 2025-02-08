package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenInfo;
import com.petstylelab.groomersunite.domain.authentication.EmailVerificationTokenService;
import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserInfo;
import com.petstylelab.groomersunite.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/users"))
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    @PostMapping("/email-verification")
    public CommonResponse<UserDto.SendRegistrationEmailVerificationResponse> sendRegistrationEmailVerification(@RequestBody @Valid UserDto.SendRegistrationEmailVerificationRequest request) {
        EmailVerificationTokenInfo emailVerificationTokenInfo = emailVerificationTokenService.sendRegistrationVerificationEmail(request.getEmail());
        UserDto.SendRegistrationEmailVerificationResponse response = new UserDto.SendRegistrationEmailVerificationResponse(emailVerificationTokenInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/recovery-verification")
    public CommonResponse<UserDto.SendAccountRecoveryVerificationResponse> sendAccountRecoveryEmailVerification(@RequestBody @Valid UserDto.SendAccountRecoveryVerificationRequest request) {
        EmailVerificationTokenInfo emailVerificationTokenInfo = emailVerificationTokenService.sendRecoveryVerificationEmail(request.getEmail());
        UserDto.SendAccountRecoveryVerificationResponse response = new UserDto.SendAccountRecoveryVerificationResponse(emailVerificationTokenInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/email-verification/confirm")
    public CommonResponse<Boolean> confirmRegistrationEmailToken(@RequestBody @Valid UserDto.EmailTokenConfirmationRequest request) {
        boolean response = emailVerificationTokenService.verifyRegistrationToken(request.getEmail(), request.getToken());
        return CommonResponse.success(response);
    }

    @PostMapping("/recovery-verification/confirm")
    public CommonResponse<Boolean> confirmAccountRecoveryEmailToken(@RequestBody @Valid UserDto.EmailTokenConfirmationRequest request) {
        boolean response = emailVerificationTokenService.verifyRecoveryToken(request.getEmail(), request.getToken());
        return CommonResponse.success(response);
    }

    @PostMapping
    public CommonResponse<UserDto.RegisterResponse> registerUser(@RequestBody @Valid UserDto.RegisterRequest request) {
        UserCommand.RegisterUserRequest command = request.toCommand();
        UserInfo userInfo = userService.registerUser(command);
        UserDto.RegisterResponse response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response);
    }

    @PatchMapping
    public CommonResponse<UserDto.ModifyUserResponse> modifyUser(@RequestBody @Valid UserDto.ModifyUserRequest request) {
        UserCommand.ModifyUserRequest command = request.toCommand();
        UserInfo userInfo = userService.modifyUser(command);
        UserDto.ModifyUserResponse response = new UserDto.ModifyUserResponse(userInfo);
        return CommonResponse.success(response);
    }

    @PatchMapping("/password")
    public CommonResponse<UserDto.ModifyPasswordResponse> modifyPassword(@RequestBody @Valid UserDto.ModifyPasswordRequest request) {
        UserCommand.ModifyPasswordRequest command = request.toCommand();
        UserInfo userInfo = userService.modifyPassword(command);
        UserDto.ModifyPasswordResponse response = new UserDto.ModifyPasswordResponse(userInfo);
        return CommonResponse.success(response);
    }


    @GetMapping("/login-id")
    public CommonResponse<UserDto.FindLoginIdResponse> findLoginId(@RequestBody @Valid UserDto.FindLoginIdRequest request) {
        String email = request.getEmail();
        UserInfo userInfo = userService.findUserByEmail(email);
        UserDto.FindLoginIdResponse response = new UserDto.FindLoginIdResponse(userInfo);
        return CommonResponse.success(response);
    }
}
