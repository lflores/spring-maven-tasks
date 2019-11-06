package com.triadsoft.units.controllers;

import com.triadsoft.api.FileUploadController;
import com.triadsoft.api.model.UploadFileResponse;
import com.triadsoft.exceptions.NotFoundException;
import com.triadsoft.services.FileStorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author triad <leonardo.flores@overactive.com>
 * Created 4/11/19 16:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadResponseTests {
    @Mock
    private FileStorageService fileStorageService;
    //@InjectMocks
    private FileUploadController fileUploadController;

    private UploadFileResponse uploadFileResponse;

    private MockMultipartFile avatar;
    private MockMultipartFile avatar2;

    @Before
    public void onLoad() throws IOException {
        when(fileStorageService.storeFile(any())).thenReturn("my-hardocded-name");
        fileUploadController = new FileUploadController(fileStorageService);
        uploadFileResponse =
                new UploadFileResponse("test-fileName", "test-downloadUri", "test-file-type", 20L);
        MockitoAnnotations.initMocks(this);

        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mate-grande.png");

        avatar = new MockMultipartFile("file",
                "mate-grande.png",
                "image/png", inputStream);

        inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mate-grande-2.png");

        avatar2 = new MockMultipartFile("file",
                "mate-grande-2.png",
                "image/png", inputStream);

    }

    @Test
    public void uploadFileResponse_constructor() {
        assertEquals("test-fileName", uploadFileResponse.getFileName());
        assertEquals("test-downloadUri", uploadFileResponse.getFileDownloadUri());
        assertEquals("test-file-type", uploadFileResponse.getFileType());
        assertEquals(20L, uploadFileResponse.getSize());
    }

    @Test
    public void uploadFileResponse_setters() {
        UploadFileResponse response =
                new UploadFileResponse("fileName", "downloadUri", "fileType", 20L);

        response.setFileName("test-fileName");
        response.setFileDownloadUri("test-downloadUri");
        response.setFileType("test-fileType");
        response.setSize(30L);

        assertEquals("test-fileName", response.getFileName());
        assertEquals("test-downloadUri", response.getFileDownloadUri());
        assertEquals("test-fileType", response.getFileType());
        assertEquals(30L, response.getSize());
    }

    @Test
    public void uploadSingleFile_ok() {
        when(fileStorageService.storeFile(any())).thenReturn("my-hardocded-name.png");
        UploadFileResponse response = fileUploadController.uploadFile(avatar);
        assertNotNull(response);
        assertEquals("my-hardocded-name", response.getFileName());
        assertEquals("image/png", response.getFileType());
        assertEquals(11842L, response.getSize());
        assertEquals("http://localhost/downloadFile/my-hardocded-name", response.getFileDownloadUri());
    }

    @Test(expected = NotFoundException.class)
    public void uploadSingleFile_nullFile() {
        when(fileStorageService.storeFile(any())).thenReturn("my-hardocded-name.png");
        UploadFileResponse response = fileUploadController.uploadFile(null);
        assertNotNull(response);
        assertEquals("my-hardocded-name", response.getFileName());
        assertEquals("image/png", response.getFileType());
        assertEquals(11842L, response.getSize());
        assertEquals("http://localhost/downloadFile/my-hardocded-name", response.getFileDownloadUri());
    }
}
