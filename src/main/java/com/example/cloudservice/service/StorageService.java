package com.example.cloudservice.service;

import com.example.cloudservice.entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface StorageService {

    void storeFile(MultipartFile file) throws IOException;

    FileData downloadFile(String filename) throws FileNotFoundException;

    void deleteFile(String filename);

    List<FileData> loadFiles(int limit);

    void updateFile(String filename, FileData newFileData) throws FileNotFoundException;
}
