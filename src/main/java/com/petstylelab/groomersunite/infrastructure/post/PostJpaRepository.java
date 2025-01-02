package com.petstylelab.groomersunite.infrastructure.post;

import com.petstylelab.groomersunite.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
