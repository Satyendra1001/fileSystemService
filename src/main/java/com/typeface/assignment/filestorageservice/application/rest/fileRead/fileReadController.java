package com.typeface.assignment.filestorageservice.application.rest.fileRead;


import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fileReadController {



    @Autowired
    private FileService fileService;

    @GetMapping("/files/{fileId}")
    public ResponseEntity<Resource> readFile(@PathVariable String fileId) {
        FileMetadata metadata = fileService.getFileMetadata(fileId);

        if (metadata != null) {
            Resource fileResource = fileService.getFileFromS3(metadata.getFileId());
            if (fileResource != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(metadata.getContentType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getFileName() + "\"")
                        .body(fileResource);
            }
        }

        return ResponseEntity.notFound().build();
    }

}
