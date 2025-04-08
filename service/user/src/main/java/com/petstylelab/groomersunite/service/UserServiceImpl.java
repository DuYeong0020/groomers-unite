package com.petstylelab.groomersunite.service;

import com.petstylelab.groomersunite.entity.Role;
import com.petstylelab.groomersunite.entity.User;
import com.petstylelab.groomersunite.service.response.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.petstylelab.groomersunite.repository.UserRepository;
import com.petstylelab.groomersunite.service.request.UserCommand;
import com.petstylelab.groomersunite.service.response.UserInfo;
import com.petstylelab.groomersunite.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserInfo registerUser(UserCommand.RegisterUserRequest request) {
        userValidator.checkRegisterUser(request);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User initUser = request.toEntity(encodedPassword, Role.NORMAL);
        User user = userRepository.save(initUser);
        return new UserInfo(user);
    }

    @Override
    @Transactional
    public UserInfo modifyUser(UserCommand.ModifyUserRequest request) {
        userValidator.checkModifyUser(request);
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        if (request.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.modifyPassword(encodedPassword);
        }
        user.modifyNickname(request.getNickname());
        user.modifyEmail(request.getEmail());
        return new UserInfo(user);
    }

    @Override
    @Transactional
    public UserInfo modifyPassword(UserCommand.ModifyPasswordRequest request) {
        userValidator.checkModifyPassword(request);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        if (request.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.modifyPassword(encodedPassword);
        }
        return new UserInfo(user);
    }

    @Override
    public UserInfo findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
        return new UserInfo(user);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        return new CustomUserDetails(user);
    }
}
