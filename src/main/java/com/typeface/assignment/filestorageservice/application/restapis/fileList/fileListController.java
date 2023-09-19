package com.typeface.assignment.filestorageservice.application.restapis.fileList;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typeface.assignment.filestorageservice.application.helpers.ResponseBuilderHelper;
import com.typeface.assignment.filestorageservice.commons.constants.ApiConstants;
import com.typeface.assignment.filestorageservice.commons.constants.CommonConstants;
import com.typeface.assignment.filestorageservice.commons.enums.ResponseEnum;
import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
    private FileService fileService;

    @Autowired
    private ResponseBuilderHelper responseBuilderHelper;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(ApiConstants.Controller.LIST_API)
    @CrossOrigin(origins = "${allowed.cross.origins}")
    public ResponseEntity<String> listFiles() {

        log.info("Request Received for {}", MDC.get(CommonConstants.REST_API));

        List<FileMetadata> files = fileService.getAllFileMetadata();

        log.info("FileMataData List:{}",files);

        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(files);
        } catch (JsonProcessingException e) {
            log.error("Parsing error Occurred in list API: ",e);
            return responseBuilderHelper.buildFailureResponse(ResponseEnum.SYSTEM_ERROR);
        }

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

}
