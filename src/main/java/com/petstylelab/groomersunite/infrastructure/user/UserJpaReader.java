package com.petstylelab.groomersunite.infrastructure.user;

import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaReader implements UserReader {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findByLoginId(String loginId) {
        return userJpaRepository.findByLoginId(loginId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }
}
