package com.petstylelab.groomersunite.domain.authentication;

import com.petstylelab.groomersunite.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String email;

    private String subject;

    @Lob
    private String body;

    private LocalDateTime expiresAt; // 토큰 만료 시간

    private LocalDateTime confirmedAt; // 토큰 확인(인증 완료) 시간

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void assignUser(User user) {
        this.user = user;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    @Builder
    public EmailVerificationToken(String token, String email, String subject, String body, LocalDateTime expiresAt, TokenType tokenType) {
        if (token == null) throw new InvalidParameterException();
        if (!StringUtils.hasText(email)) throw new InvalidParameterException();
        if (!StringUtils.hasText(subject)) throw new InvalidParameterException();
        if (!StringUtils.hasText(body)) throw new InvalidParameterException();
        if (expiresAt == null) throw new InvalidParameterException();
        if (tokenType == null) throw new InvalidParameterException();

        this.token = token;
        this.email = email;
        this.subject = subject;
        this.body = body;
        this.expiresAt = expiresAt;
        this.tokenType = tokenType;
    }
}
