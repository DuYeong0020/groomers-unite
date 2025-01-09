package com.petstylelab.groomersunite.infrastructure.comment;

import com.petstylelab.groomersunite.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
