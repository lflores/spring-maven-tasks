package com.triadsoft.api;

import com.triadsoft.api.model.UploadFileResponse;
import com.triadsoft.exceptions.NotFoundException;
import com.triadsoft.services.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 26/04/19 15:02
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FileUploadController {
    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws NotFoundException {
        return uploadFileSingle(file);
    }

    @PostMapping(value = "/uploadMultipleFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestPart("file") List<MultipartFile> files) throws NotFoundException {
        return files
                .stream()
                .map(this::uploadFileSingle)
                .collect(Collectors.toList());
    }

    private UploadFileResponse uploadFileSingle(MultipartFile file) throws NotFoundException {
        if (Objects.isNull(file)) {
            throw new NotFoundException("The file cannot be null");
        }
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = fileStorageService.getContentType(resource);
        long size = fileStorageService.getContentSize(resource);

        if (Objects.isNull(resource)) {
            throw new NotFoundException(String.format("The file %s cannot be found", fileName));
        }

        return ResponseEntity.ok()
                .contentLength(size)
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
