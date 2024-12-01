package com.petstylelab.groomersunite.domain.user;

import com.petstylelab.groomersunite.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String email;
    private String password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;

}
