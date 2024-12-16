package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.common.response.CommonResponse;
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
