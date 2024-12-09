package com.petstylelab.groomersunite.infrastructure.user;

import com.petstylelab.groomersunite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
