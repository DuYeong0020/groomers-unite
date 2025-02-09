package com.petstylelab.groomersunite.domain.user;

public interface UserReader {
    User findById(Long id);
    User findByLoginId(String loginId);
    User findByEmail(String email);
}
