package com.petstylelab.groomersunite.infrastructure.file;

import com.petstylelab.groomersunite.domain.file.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3FileUploader implements FileUploader {
    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    private final S3Client s3Client;

    @Async
    @Override
    public void saveFile(MultipartFile file, String storeFileName) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(storeFileName)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new IllegalStateException("S3 파일 업로드 중 문제가 발생했습니다.", e);
        }
    }

    @Async
    @Override
    public void deleteFile(String storeFileName) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(storeFileName)
                .build();
        s3Client.deleteObject(deleteRequest);
    }

    @Override
    public String getFileUrl(String storeFileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, storeFileName);
    }

}
