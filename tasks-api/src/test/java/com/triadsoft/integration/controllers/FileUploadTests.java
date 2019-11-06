package com.triadsoft.integration.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 27/04/19 08:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadTests {
    @Autowired
    private MockMvc mockMvc;

    private MockMultipartFile avatar;
    private MockMultipartFile avatar2;

    @Before
    public void setUp() throws IOException {
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
    public void uploadOneFile() throws Exception {
        mockMvc.perform(fileUpload("/uploadFile").file(avatar))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("mate-grande.png"))
                .andExpect(jsonPath("$.fileDownloadUri").value("http://localhost/downloadFile/mate-grande.png"))
                .andExpect(jsonPath("$.fileType").value("image/png"))
                .andExpect(jsonPath("$.size").value("11842"))
                .andReturn();
    }

    @Test(expected = IllegalArgumentException.class)
    public void uploadOneFile_notFound() throws Exception {
        mockMvc.perform(fileUpload("/uploadFile").file(null))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("No se encontró la tarea con el id: 15")))
                .andReturn();
    }

    @Test
    public void uploadMultipleFiles() throws Exception {
        mockMvc.perform(multipart("/uploadMultipleFiles")
                .file(avatar)
                .file(avatar2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fileName").value("mate-grande.png"))
                .andExpect(jsonPath("$[0].fileDownloadUri").value("http://localhost/downloadFile/mate-grande.png"))
                .andExpect(jsonPath("$[0].fileType").value("image/png"))
                .andExpect(jsonPath("$[0].size").value("11842"))
                .andExpect(jsonPath("$[1].fileName").value("mate-grande-2.png"))
                .andExpect(jsonPath("$[1].fileDownloadUri").value("http://localhost/downloadFile/mate-grande-2.png"))
                .andExpect(jsonPath("$[1].fileType").value("image/png"))
                .andExpect(jsonPath("$[1].size").value("11842"))
                .andReturn();
    }

    @Test
    public void downloadFile_ok() throws Exception {
        mockMvc.perform(get("/downloadFile/diagram.png").contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
                .andExpect(content().contentType("image/png"))
                .andDo(print());
    }
}
