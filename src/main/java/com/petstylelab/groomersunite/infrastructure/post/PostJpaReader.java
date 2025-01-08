package com.petstylelab.groomersunite.infrastructure.post;

import com.petstylelab.groomersunite.domain.post.*;
import com.petstylelab.groomersunite.domain.user.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostJpaReader implements PostReader {

    private final PostJpaRepository postJpaRepository;
    private final JPAQueryFactory queryFactory;


    @Override
    public Post findById(Long id) {
        return postJpaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<PostSummary> findByCriteria(PostCriteria.GetPosts criteria) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        JPAQuery<PostSummary> query = queryFactory
                .select(new QPostSummary(
                        post.id,
                        post.title,
                        user.nickname,
                        post.createdAt))
                .from(post)
                .join(post.user, user)
                .where(buildKeywordCondition(criteria.getKeyword()));

        List<PostSummary> content = query
                .offset(criteria.getPageable().getOffset())
                .limit(criteria.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(content, criteria.getPageable(), content.size());
    }

    private BooleanExpression buildKeywordCondition(String keyword) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        return post.title.containsIgnoreCase(keyword)
                .or(Expressions.stringTemplate("CAST({0} AS string)", post.content).containsIgnoreCase(keyword))
                .or(user.nickname.containsIgnoreCase(keyword));
    }
}