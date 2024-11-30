package com.petstylelab.groomersunite.domain.user;

import com.petstylelab.groomersunite.domain.BaseEntity;
import com.petstylelab.groomersunite.domain.board.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login_id;
    private String password;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

}
