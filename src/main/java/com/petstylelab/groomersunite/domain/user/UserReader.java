package com.petstylelab.groomersunite.domain.user;

public interface UserReader {
    User findByLoginId(String loginId);
}
