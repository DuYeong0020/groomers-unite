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

    @PostMapping
    public CommonResponse registerUser(@RequestBody @Valid UserDto.RegisterRequest request) {
        return null;
    }
}
