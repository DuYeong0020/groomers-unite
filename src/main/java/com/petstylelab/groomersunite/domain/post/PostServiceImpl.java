package com.petstylelab.groomersunite.domain.post;

import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

import static com.petstylelab.groomersunite.common.util.FileNameUtil.createStoreFileName;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostReader postReader;
    private final UserReader userReader;
    private final PostStore postStore;
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    @Override
    @Transactional
    public PostInfo createPost(PostCommand.CreatePostRequest request) {
        User user = userReader.findByLoginId(request.getLoginId());
        Post initPost = request.toEntity(user);

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
                PostImage postImage = new PostImage(originalFilename, storeFileName, fileUrl);
                initPost.addImage(postImage);
            }
        }
        Post post = postStore.storePost(initPost);
        return new PostInfo(post);
    }

    @Override
    @Transactional
    public PostInfo updatePost(PostCommand.UpdatePostRequest request, Long postId) {
        Post post = postReader.findById(postId);

        for (String deleteImageUrl : request.getDeleteImageUrls()) {
            PostImage deleteImage = post.getImages().stream()
                    .filter(image -> image.getUrl().equals(deleteImageUrl))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("삭제할 이미지를 찾을 수 없습니다."));

            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(deleteImageUrl)
                    .build();
            s3Client.deleteObject(deleteRequest);
            post.removeImage(deleteImage);
        }

        for (MultipartFile newImage : request.getNewImages()) {
            if (!newImage.isEmpty()) {
                String originalFilename = newImage.getOriginalFilename();
                String storeFileName = createStoreFileName(originalFilename);
                try {
                    s3Client.putObject(
                            PutObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(storeFileName)
                                    .build(),
                            RequestBody.fromInputStream(newImage.getInputStream(), newImage.getSize()));
                } catch (IOException e) {
                    throw new IllegalStateException("S3 파일 업로드 중 문제가 발생했습니다.", e);
                }
                String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, storeFileName);
                PostImage postImage = new PostImage(originalFilename, storeFileName, fileUrl);
                post.addImage(postImage);
            }
        }

        post.modifyTitle(request.getTitle());
        post.modifyContent(request.getContent());

        return new PostInfo(post);
    }
}
