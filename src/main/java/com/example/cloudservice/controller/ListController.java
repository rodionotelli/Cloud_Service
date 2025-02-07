package com.example.cloudservice.controller;

import com.example.cloudservice.entity.FileData;
import com.example.cloudservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListController {

    private final StorageService storageService;

    @GetMapping("/list")
    public ResponseEntity<List<FileData>> getAllFiles(@RequestParam int limit) {
        List<FileData> files = storageService.loadFiles(limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(files);
    }
}
