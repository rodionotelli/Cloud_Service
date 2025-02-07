package com.example.cloudservice.repository;

import com.example.cloudservice.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDBRepository extends JpaRepository<FileData, Long> {

    void deleteFileDataByFilename(String filename);

    Optional<FileData> findFileDataByFilename(String filename);
}
