package com.petstylelab.groomersunite.common.util;

import java.util.UUID;

public class FileNameUtil {

    public static String createStoreFileName(String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFileName);
        return uuid + "." + ext;
    }

    public static String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        if (pos == -1) {
            throw new IllegalArgumentException("Invalid file name: " + originalFileName);
        }
        return originalFileName.substring(pos + 1);
    }
}
