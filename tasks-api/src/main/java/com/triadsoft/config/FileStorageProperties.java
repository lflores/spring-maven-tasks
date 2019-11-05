package com.triadsoft.config;/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 26/04/19 14:50
 */

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 26/04/19 14:50
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
