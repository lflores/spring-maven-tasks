package com.triadsoft.integration.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void uploadOneFile() throws Exception {
        final InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("mate-grande.png");
        final MockMultipartFile avatar = new MockMultipartFile("file",
                "mate-grande.png",
                "image/png", inputStream);

        final MvcResult result = mockMvc.perform(fileUpload("/uploadFile").file(avatar))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("mate-grande.png"))
                .andExpect(jsonPath("$.fileDownloadUri").value("http://localhost/downloadFile/mate-grande.png"))
                .andExpect(jsonPath("$.fileType").value("image/png"))
                .andExpect(jsonPath("$.size").value("11842"))
                .andReturn();
    }
}
