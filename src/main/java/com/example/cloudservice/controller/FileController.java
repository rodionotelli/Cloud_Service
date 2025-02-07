package com.example.cloudservice.controller;

import com.example.cloudservice.entity.FileData;
import com.example.cloudservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        storageService.storeFile(file);
        return ResponseEntity.ok().body("Success upload");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam("filename") String filename) {
        storageService.deleteFile(filename);
        return ResponseEntity.ok().body("Success deleted");
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) throws FileNotFoundException {
        FileData file = storageService.downloadFile(filename);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFilename())
                .body(file.getFile());
    }

    @PutMapping
    public ResponseEntity<String> updateFile(@RequestParam("filename") String filename,
                                             @RequestBody() FileData newFileData) throws FileNotFoundException {
        storageService.updateFile(filename, newFileData);
        return ResponseEntity.ok().body("Success upload");
    }
}
