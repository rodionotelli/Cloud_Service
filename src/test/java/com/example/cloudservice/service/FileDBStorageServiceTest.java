package com.example.cloudservice.service;

import com.example.cloudservice.entity.FileData;
import com.example.cloudservice.repository.FileDBRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class FileDBStorageServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileDBRepository fileDBRepository;

    @Container
    private static final PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @BeforeEach
    void setUp() {
        fileDBRepository.deleteAll();
        fileDBRepository.save(FileData.builder()
                .filename("test.txt")
                .file("Feugiatlacinia".getBytes())
                .size((long) "Feugiatlacinia".getBytes().length)
                .build());
        fileDBRepository.save(FileData.builder()
                .filename("test2.txt")
                .file("Mattisfacilisi".getBytes())
                .size((long) "Mattisfacilisi".getBytes().length)
                .build());
    }

    @Test
    @SneakyThrows
    @Transactional
    void storeFile() {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "test3.txt",
                "text/plain",
                "Spring Framework".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("http://localhost:8080/file")
                        .file(multipartFile))
                .andExpect(status().is2xxSuccessful());

        assertFalse(fileDBRepository.findFileDataByFilename("test3.txt").isEmpty());
        assertEquals(multipartFile.getBytes(), fileDBRepository.findFileDataByFilename("test3.txt").get().getFile());
    }

    @Test
    @SneakyThrows
    void loadFiles() {
        var result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/list?limit=3"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertEquals(new ObjectMapper().writeValueAsString(fileDBRepository.findAll()),
                result.getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void downloadFile() {
        var result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/file?filename=test.txt"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertArrayEquals("Feugiatlacinia".getBytes(), result.getResponse().getContentAsByteArray());
    }

    @Test
    @SneakyThrows
    @Transactional
    void deleteFile() {
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/file?filename=test.txt"))
                .andExpect(status().is2xxSuccessful());

        assertTrue(fileDBRepository.findFileDataByFilename("test.txt").isEmpty());
        assertFalse(fileDBRepository.findFileDataByFilename("test2.txt").isEmpty());
    }

    @Test
    @SneakyThrows
    @Transactional
    void updateFile() {
        FileData fileData = FileData.builder()
                .filename("test3.txt")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/file?filename=test.txt")
                        .content(new ObjectMapper().writeValueAsString(fileData))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        assertTrue(fileDBRepository.findFileDataByFilename("test.txt").isEmpty());
        assertFalse(fileDBRepository.findFileDataByFilename("test3.txt").isEmpty());
    }
}