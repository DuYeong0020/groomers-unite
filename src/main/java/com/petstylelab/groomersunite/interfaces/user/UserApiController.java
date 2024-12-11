package com.petstylelab.groomersunite.interfaces.user;

import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/users")
    public CommonResponse<UserDto.RegisterResponse> registerUser(@RequestBody @Valid UserDto.RegisterRequest request) {
        var command = request.toCommand();
        var userInfo = userService.registerUser(command);
        var response = new UserDto.RegisterResponse(userInfo);

        return CommonResponse.success(response);
    }
}
