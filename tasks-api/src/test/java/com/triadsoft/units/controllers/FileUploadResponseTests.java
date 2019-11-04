package com.triadsoft.units.controllers;

import com.triadsoft.api.model.UploadFileResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
/**
 * @author triad <leonardo.flores@overactive.com>
 * Created 4/11/19 16:24
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class FileUploadResponseTests {
    private UploadFileResponse response;

    @Test
    public void uploadFileResponse_constructor(){
        UploadFileResponse response =
                new UploadFileResponse("test-fileName","test-downloadUri","test-file-type",20L);
        assertEquals("test-fileName",response.getFileName());
        assertEquals("test-downloadUri",response.getFileDownloadUri());
        assertEquals("test-file-type",response.getFileType());
        assertEquals(20L,response.getSize());
    }

    @Test
    public void uploadFileResponse_setters(){
        UploadFileResponse response =
                new UploadFileResponse("fileName","downloadUri","fileType",20L);

        response.setFileName("test-fileName");
        response.setFileDownloadUri("test-downloadUri");
        response.setFileType("test-fileType");
        response.setSize(30L);

        assertEquals("test-fileName",response.getFileName());
        assertEquals("test-downloadUri",response.getFileDownloadUri());
        assertEquals("test-fileType",response.getFileType());
        assertEquals(30L,response.getSize());
    }
}
