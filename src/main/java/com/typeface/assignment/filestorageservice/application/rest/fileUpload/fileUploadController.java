package com.typeface.assignment.filestorageservice.application.rest.fileUpload;

import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
public class fileUploadController {

    @Autowired
    private FileService fileService;

    @PostMapping("/files/upload")
    @CrossOrigin(origins = "http://localhost:8090")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, @RequestParam("metadata") String metadata) {
        String fileKey = fileService.uploadFileToS3(file, fileName);
        FileMetadata filemetadata = new FileMetadata(fileKey, fileName,file.getContentType(), new Date(),new Date(), file.getSize());
        fileService.saveFileMetadata(filemetadata);

        return ResponseEntity.ok(fileKey);
    }
}
