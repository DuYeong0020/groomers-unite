package com.petstylelab.groomersunite.infrastructure.user;

import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserJpaStore implements UserStore {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User storeUser(User user) {
        return userJpaRepository.save(user);
    }
}
