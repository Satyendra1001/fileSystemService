package com.typeface.assignment.filestorageservice.application.rest.fileDelete;

import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import com.typeface.assignment.filestorageservice.infrastructure.repositories.FileMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class fileDeleteController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;
    @DeleteMapping("/files/{fileId}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {


        FileMetadata fileMetadata = fileMetadataRepository.findByFileId(fileId);

        if (fileMetadata == null) {
            return ResponseEntity.notFound().build();
        }
        // Delete the file from Amazon S3
        boolean deleted = fileService.deleteFileFromS3(fileMetadata.getFileId());

        if (deleted) {
            fileService.deleteFileMetadata(fileId);
            log.info("File deleted successfully, fileId :{}",fileId);
            return ResponseEntity.ok("File deleted successfully");
        }

        return ResponseEntity.notFound().build();
    }

}
