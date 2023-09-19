package com.typeface.assignment.filestorageservice.application.restapis.fileRead;


import com.typeface.assignment.filestorageservice.commons.constants.ApiConstants;
import com.typeface.assignment.filestorageservice.commons.constants.CommonConstants;
import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class fileReadController {



    @Autowired
    private FileService fileService;


    @GetMapping(ApiConstants.Controller.READ_API)
    public ResponseEntity<Resource> readFile(@PathVariable String fileId) {

        log.info("Request Received for {}, fileId {}", MDC.get(CommonConstants.REST_API),fileId);

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
