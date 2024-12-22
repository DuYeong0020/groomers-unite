package com.petstylelab.groomersunite.domain.authentication;

public interface EmailSender {
    void sendEmail(String email, String subject, String body);
}
