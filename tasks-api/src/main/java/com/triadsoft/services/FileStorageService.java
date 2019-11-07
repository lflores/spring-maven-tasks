package com.triadsoft.services;

import com.triadsoft.config.FileStorageProperties;
import com.triadsoft.exceptions.FileStorageException;
import com.triadsoft.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 26/04/19 14:49
 */
@Service
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;
    private final ServletContext servletContext;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties, ServletContext servletContext) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.servletContext = servletContext;
    }

    @PostConstruct
    private void init(){
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        //Agrego una imagen por default que será agregada a una tarea por default.
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("diagram.png");
        Path targetLocation = this.fileStorageLocation.resolve("diagram.png");
        try {
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the file by default.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new NotFoundException("File not found " + fileName, ex);
        }
    }

    public String getContentType(Resource resource){
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = servletContext.getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.error("Could not determine file type.");
            throw new FileStorageException("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    public long getContentSize(Resource resource){
        long size =-1;
        try {
            size = resource.getFile().length();
        } catch (IOException e) {
            throw new NotFoundException(String.format("The file %s cannot be found",resource.getFilename()));
        }
        if(size<0){
            throw new NotFoundException(String.format("The file size for file %s cannot be found",resource.getFilename()));
        }
        return size;
    }
}
