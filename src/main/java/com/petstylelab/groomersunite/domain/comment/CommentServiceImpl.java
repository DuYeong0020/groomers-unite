package com.petstylelab.groomersunite.domain.comment;

import com.petstylelab.groomersunite.domain.post.Post;
import com.petstylelab.groomersunite.domain.post.PostReader;
import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static com.petstylelab.groomersunite.common.util.FileNameUtil.createStoreFileName;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostReader postReader;
    private final UserReader userReader;
    private final CommentStore commentStore;
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    @Override
    @Transactional
    public CommentInfo createComment(CommentCommand.CreateCommentRequest request) {
        Post post = postReader.findById(request.getPostId());
        User user = userReader.findByLoginId(request.getLoginId());

        Comment initComment = request.toEntity(post, user);

        for (MultipartFile imageFile : request.getImageFiles()) {
            if (!imageFile.isEmpty()) {
                String originalFilename = imageFile.getOriginalFilename();
                String storeFileName = createStoreFileName(originalFilename);

                try {
                    s3Client.putObject(
                            PutObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(storeFileName)
                                    .build(),
                            RequestBody.fromInputStream(imageFile.getInputStream(), imageFile.getSize()));
                } catch (IOException e) {
                    throw new IllegalStateException("S3 파일 업로드 중 문제가 발생했습니다.", e);
                }
                String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, storeFileName);
                CommentImage commentImage = new CommentImage(originalFilename, storeFileName, fileUrl);
                initComment.addImage(commentImage);
            }
        }
        Comment comment = commentStore.storeComment(initComment);
        return new CommentInfo(comment);
    }
}
