package com.petstylelab.groomersunite.infrastructure.comment;

import com.petstylelab.groomersunite.domain.comment.*;
import com.petstylelab.groomersunite.domain.comment.rating.QRating;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentJpaReader implements CommentReader {

    private final CommentJpaRepository commentJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Comment findById(Long id) {
        return commentJpaRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<CommentDetail> findByCriteria(CommentCriteria.GetComments criteria) {
        QComment comment = QComment.comment;
        QRating rating = QRating.rating;
        QCommentImage commentImage = QCommentImage.commentImage;

        List<CommentDetail> content = jpaQueryFactory
                .select(Projections.constructor(CommentDetail.class,
                        comment.id,
                        comment.content,
                        comment.user.id,
                        Expressions.stringTemplate("GROUP_CONCAT({0})", commentImage.url),
                        Projections.constructor(CommentDetail.RatingDetail.class,
                                rating.completeness,
                                rating.finish,
                                rating.symmetry,
                                rating.balance)
                ))
                .from(comment)
                .leftJoin(comment.images, commentImage)
                .leftJoin(comment.rating, rating)
                .where(comment.post.id.eq(criteria.getPostId()))
                .groupBy(comment.id)
                .offset(criteria.getPageable().getOffset())
                .limit(criteria.getPageable().getPageSize())
                .fetch();

        return new PageImpl<>(content, criteria.getPageable(), content.size());
    }
}
