package com.typeface.assignment.filestorageservice.infrastructure.repositories;

import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

    FileMetadata findByFileId(String fieldId);
    void deleteByFileId(String fieldId);


}

