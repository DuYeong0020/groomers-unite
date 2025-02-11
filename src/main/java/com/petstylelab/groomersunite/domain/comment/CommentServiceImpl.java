package com.petstylelab.groomersunite.domain.comment;

import com.petstylelab.groomersunite.domain.comment.rating.Rating;
import com.petstylelab.groomersunite.domain.file.FileUploader;
import com.petstylelab.groomersunite.domain.post.Post;
import com.petstylelab.groomersunite.domain.post.PostReader;
import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

import static com.petstylelab.groomersunite.common.util.FileNameUtil.createStoreFileName;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostReader postReader;
    private final CommentReader commentReader;
    private final UserReader userReader;
    private final CommentStore commentStore;
    private final FileUploader fileUploader;


    @Override
    public CommentInfo getCommentById(Long postId, Long commentId) {
        Comment comment = commentReader.findById(commentId);
        return new CommentInfo(comment);
    }

    @Override
    public Page<CommentDetail> getCommentsByCriteria(CommentCriteria.GetComments criteria) {
        return commentReader.findByCriteria(criteria);
    }

    @Override
    @Transactional
    public CommentInfo createComment(CommentCommand.CreateCommentRequest request) {
        Post post = postReader.findById(request.getPostId());
        User user = userReader.findById(request.getUserId());

        Comment initComment = request.toEntity(post, user);

        for (MultipartFile imageFile : request.getImageFiles()) {
            if (!imageFile.isEmpty()) {
                String originalFilename = imageFile.getOriginalFilename();
                String storeFileName = createStoreFileName(originalFilename);

                fileUploader.saveFile(imageFile, storeFileName);
                String fileUrl = fileUploader.getFileUrl(storeFileName);

                CommentImage commentImage = new CommentImage(originalFilename, storeFileName, fileUrl);
                initComment.addImage(commentImage);
            }
        }
        Comment comment = commentStore.storeComment(initComment);
        return new CommentInfo(comment);
    }

    @Override
    @Transactional
    public CommentInfo updateComment(CommentCommand.UpdateCommentRequest request) {
        Comment comment = commentReader.findById(request.getCommentId());
        comment.modifyContent(request.getContent());
        Rating initEntity = Optional.ofNullable(request.getRating())
                .map(CommentCommand.UpdateCommentRequest.RatingRequest::toEntity)
                .orElse(null);

        comment.modifyRating(initEntity);

        for (String deleteImageName : request.getDeleteImageNames()) {
            CommentImage deleteImage = comment.getImages().stream()
                    .filter(image -> image.getStoreFileName().equals(deleteImageName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("삭제할 이미지를 찾을 수 없습니다."));

            fileUploader.deleteFile(deleteImage.getStoreFileName());
            comment.removeImage(deleteImage);
        }

        for (MultipartFile newImage : request.getNewImages()) {
            if (!newImage.isEmpty()) {
                String originalFilename = newImage.getOriginalFilename();
                String storeFileName = createStoreFileName(originalFilename);
                fileUploader.saveFile(newImage, storeFileName);
                String fileUrl = fileUploader.getFileUrl(storeFileName);
                CommentImage commentImage = new CommentImage(originalFilename, storeFileName, fileUrl);
                comment.addImage(commentImage);
            }
        }
        return new CommentInfo(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentReader.findById(commentId);
        comment.getImages().forEach(image -> {
            fileUploader.deleteFile(image.getStoreFileName());
        });
        commentStore.deleteComment(comment);
    }
}
