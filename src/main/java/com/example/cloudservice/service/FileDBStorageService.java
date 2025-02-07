package com.example.cloudservice.service;

import com.example.cloudservice.entity.FileData;
import com.example.cloudservice.repository.FileDBRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileDBStorageService implements StorageService {

    private final FileDBRepository fileDBRepository;

    @Override
    public void storeFile(MultipartFile file) throws IOException {
        fileDBRepository.save(FileData.builder()
                .filename(file.getOriginalFilename())
                .file(file.getBytes())
                .size(file.getSize()).build());
    }

    @Override
    @Transactional
    public FileData downloadFile(String filename) throws FileNotFoundException {
        Optional<FileData> optionalFile = fileDBRepository.findFileDataByFilename(filename);
        if (optionalFile.isEmpty()) {
            throw new FileNotFoundException("File is not found.");
        }
        return optionalFile.get();
    }

    @Override
    @Transactional
    public void deleteFile(String filename) {
        fileDBRepository.deleteFileDataByFilename(filename);
    }

    @Override
    public List<FileData> loadFiles(int limit) {
        PageRequest page = PageRequest.of(0, limit);
        return fileDBRepository.findAll(page).getContent();
    }

    @Override
    @Transactional
    public void updateFile(String filename, FileData newFileData) throws FileNotFoundException {
        Optional<FileData> optionalFile = fileDBRepository.findFileDataByFilename(filename);
        if (optionalFile.isEmpty()) {
            throw new FileNotFoundException("File is not found.");
        }
        FileData fileData = optionalFile.get();
        if (!newFileData.getFilename().isEmpty()) {
            fileData.setFilename(newFileData.getFilename());
        }
        fileDBRepository.save(fileData);

    }
}
