package com.example.cloudservice.controller;

import com.example.cloudservice.entity.FileData;
import com.example.cloudservice.service.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ListControllerTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private ListController listController;

    @Test
    void getAllFiles_SuccessGetListWithLimit3() {
        // given
        int limit = 3;
        byte[] file1 = "Spring Framework1".getBytes();
        String filename1 = "test1.txt";
        FileData fileData1 = FileData.builder()
                .file(file1)
                .size((long) file1.length)
                .filename(filename1).build();
        byte[] file2 = "Spring Framework2".getBytes();
        String filename2 = "test2.txt";
        FileData fileData2 = FileData.builder()
                .file(file2)
                .size((long) file2.length)
                .filename(filename2).build();
        List<FileData> list = List.of(fileData1, fileData2);

        doReturn(list).when(this.storageService).loadFiles(limit);
        // when
        ResponseEntity<List<FileData>> responseEntity = this.listController.getAllFiles(limit);
        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(list.size(), responseEntity.getBody().size());
    }
}