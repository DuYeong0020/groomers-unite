package com.petstylelab.groomersunite.domain.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {
    void saveFile(MultipartFile file, String storeFileName);
    void deleteFile(String storeFileName);
    String getFileUrl(String storeFileName);
}
