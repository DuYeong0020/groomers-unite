package com.petstylelab.groomersunite.infrastructure.user;

import com.petstylelab.groomersunite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);
}
