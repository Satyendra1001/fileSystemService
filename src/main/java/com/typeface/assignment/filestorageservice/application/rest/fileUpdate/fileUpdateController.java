package com.typeface.assignment.filestorageservice.application.rest.fileUpdate;

import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
public class fileUpdateController {


    @Autowired
    private FileService fileService;
    @PutMapping("/{fileId}")
    public ResponseEntity<String> updateFile(@PathVariable String fileId, @RequestParam("file") MultipartFile file, @RequestParam("metadata") String metadata) {

        String updatedFileKey = fileService.updateFileInS3(fileId, file);

        if (updatedFileKey != null) {
            FileMetadata updatedMetadata = new FileMetadata(fileId, file.getName(),file.getContentType(), new Date(),new Date(), file.getSize());
            fileService.updateFileMetadata(updatedMetadata);

            // Return updated metadata or a success message
            return ResponseEntity.ok("File updated successfully");
        }

        return ResponseEntity.notFound().build();
    }

}
