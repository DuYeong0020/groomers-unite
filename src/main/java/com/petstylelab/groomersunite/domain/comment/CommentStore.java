package com.petstylelab.groomersunite.domain.comment;

public interface CommentStore {

    Comment storeComment(Comment comment);

    void deleteComment(Comment comment);
}
