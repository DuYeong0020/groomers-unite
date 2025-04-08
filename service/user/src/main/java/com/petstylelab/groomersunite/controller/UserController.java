package com.petstylelab.groomersunite.controller;

import com.petstylelab.common.argumentresolver.Login;
import com.petstylelab.common.response.CommonResponse;
import com.petstylelab.groomersunite.controller.dto.UserDto;
import com.petstylelab.groomersunite.service.UserService;
import com.petstylelab.groomersunite.service.request.UserCommand;
import com.petstylelab.groomersunite.service.response.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/api/users"))
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CommonResponse<UserDto.RegisterResponse> registerUser(@RequestBody @Valid UserDto.RegisterRequest request) {
        UserCommand.RegisterUserRequest command = request.toCommand();
        UserInfo userInfo = userService.registerUser(command);
        UserDto.RegisterResponse response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response);
    }

    @PatchMapping
    public CommonResponse<UserDto.ModifyUserResponse> modifyUser(@Login String loginId, @RequestBody @Valid UserDto.ModifyUserRequest request) {
        UserCommand.ModifyUserRequest command = request.toCommand(loginId);
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
