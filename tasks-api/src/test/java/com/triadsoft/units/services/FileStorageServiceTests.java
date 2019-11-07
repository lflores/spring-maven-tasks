package com.triadsoft.units.services;

import com.triadsoft.config.FileStorageProperties;
import com.triadsoft.services.FileStorageService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;

import static org.mockito.Mockito.*;

/**
 * @author triad <leonardo.flores@overactive.com>
 * Created 4/11/19 16:01
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class FileStorageServiceTests {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Mock
    private FileStorageProperties fileStorageProperties;

    @Mock
    private FileStorageService fileStorageService;

    @Autowired
    ServletContext servletContext;

    @Before
    public void onLoad(){
        when(fileStorageProperties.getUploadDir()).thenReturn("dummy-dir");
        fileStorageService = new FileStorageService(fileStorageProperties,servletContext);
    }

    @Test
    public void setFileStorage_loadFiles(){

    }
}
