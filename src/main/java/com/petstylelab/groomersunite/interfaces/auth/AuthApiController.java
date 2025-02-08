package com.petstylelab.groomersunite.interfaces.auth;

import com.petstylelab.groomersunite.common.response.CommonResponse;
import com.petstylelab.groomersunite.domain.user.UserCommand;
import com.petstylelab.groomersunite.domain.user.UserInfo;
import com.petstylelab.groomersunite.domain.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApiController {
    private final UserService userService;

    @PostMapping("/login")
    public CommonResponse<Boolean> login(@RequestBody @Valid AuthDto.LoginRequest request, HttpSession session) {
        UserCommand.AuthenticateUserRequest command = request.toCommand();
        UserInfo userInfo = userService.authenticateUser(command);
        session.setAttribute("userId", userInfo.getId());
        return CommonResponse.success(true);
    }

    @PostMapping("logout")
    public CommonResponse<Boolean> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return CommonResponse.success(true);
    }
}
