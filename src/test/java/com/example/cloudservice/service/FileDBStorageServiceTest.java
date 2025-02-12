package com.example.cloudservice.service;

import com.example.cloudservice.entity.FileData;
import com.example.cloudservice.repository.FileDBRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FileDBStorageService.class})
@ExtendWith(SpringExtension.class)
public class FileDBStorageServiceTest {
    @MockBean
    private FileDBRepository fileDBRepositoryTest;

    @Autowired
    private FileDBStorageService fileDBStorageServiceTest;

    @Test
    @SneakyThrows
    void storeFile() {
        //arrange
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test.txt", new FileInputStream("./src/test/resources/test.txt"));
        FileData testFileData = new FileData();
        when(fileDBRepositoryTest.save(Mockito.any(FileData.class))).thenReturn(testFileData);

        //act
        fileDBStorageServiceTest.storeFile(mockMultipartFile);

        //assert
        verify(fileDBRepositoryTest).save(Mockito.any());
    }

    @Test
    void deleteFile() {
        //arrange
        doNothing().when(fileDBRepositoryTest).deleteFileDataByFilename(Mockito.any());

        //act
        fileDBStorageServiceTest.deleteFile(Mockito.any());

        //assert
        verify(fileDBRepositoryTest).deleteFileDataByFilename(Mockito.any());
    }
}
