package com.typeface.assignment.filestorageservice.application.rest.fileList;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.infrastructure.repositories.FileMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class fileListController {

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/files")
    @CrossOrigin(origins = "http://localhost:8090")
    public ResponseEntity<String> listFiles() {
        List<FileMetadata> files = fileMetadataRepository.findAll();
        log.info("FileMataData List:{}",files);
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(files);
        } catch (JsonProcessingException e) {
            log.error("Parsing error Occurred : ",e);
            return null;
        }

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

}
