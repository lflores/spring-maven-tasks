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

    @Before
    public void onLoad(){
        when(fileStorageProperties.getUploadDir()).thenReturn("dummy-dir");
        fileStorageService = new FileStorageService(fileStorageProperties);
    }

    @Test
    public void setFileStorage_loadFiles(){

    }
}
