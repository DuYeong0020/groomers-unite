package com.petstylelab.groomersunite.domain.post;

import com.petstylelab.groomersunite.domain.file.FileUploader;
import com.petstylelab.groomersunite.domain.user.User;
import com.petstylelab.groomersunite.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.petstylelab.groomersunite.common.util.FileNameUtil.createStoreFileName;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostReader postReader;
    private final UserReader userReader;
    private final PostStore postStore;
    private final FileUploader fileUploader;

    @Override
    public PostInfo getPostById(Long postId) {
        Post post = postReader.findById(postId);
        return new PostInfo(post);
    }

    @Override
    public Page<PostSummary> getPostsByCriteria(PostCriteria.GetPosts request) {
        return postReader.findByCriteria(request);
    }

    @Override
    @Transactional
    public PostInfo createPost(PostCommand.CreatePostRequest request) {
        User user = userReader.findById(request.getUserId());
        Post initPost = request.toEntity(user);

        for (MultipartFile imageFile : request.getImageFiles()) {
            if (!imageFile.isEmpty()) {
                System.out.println("imageFile = " + imageFile);
                String originalFilename = imageFile.getOriginalFilename();
                String storeFileName = createStoreFileName(originalFilename);

                fileUploader.saveFile(imageFile, storeFileName);
                String fileUrl = fileUploader.getFileUrl(storeFileName);

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

        for (String deleteImageName : request.getDeleteImageNames()) {
            PostImage deleteImage = post.getImages().stream()
                    .filter(image -> image.getStoreFileName().equals(deleteImageName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("삭제할 이미지를 찾을 수 없습니다."));

            fileUploader.deleteFile(deleteImageName);

            post.removeImage(deleteImage);
        }

        for (MultipartFile newImage : request.getNewImages()) {
            if (!newImage.isEmpty()) {
                String originalFilename = newImage.getOriginalFilename();
                String storeFileName = createStoreFileName(originalFilename);

                fileUploader.saveFile(newImage, storeFileName);

                String fileUrl = fileUploader.getFileUrl(storeFileName);
                PostImage postImage = new PostImage(originalFilename, storeFileName, fileUrl);
                post.addImage(postImage);
            }
        }

        post.modifyTitle(request.getTitle());
        post.modifyContent(request.getContent());

        return new PostInfo(post);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postReader.findById(postId);
        post.getComments()
                .forEach(comment -> comment.getImages().forEach(
                        image -> {
                            fileUploader.deleteFile(image.getStoreFileName());
                        }));

        post.getImages().forEach(image -> {
            fileUploader.deleteFile(image.getStoreFileName());
        });
        postStore.deletePost(post);
    }
}
