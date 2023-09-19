package com.typeface.assignment.filestorageservice.application.restapis.fileUpdate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typeface.assignment.filestorageservice.application.helpers.ResponseBuilderHelper;
import com.typeface.assignment.filestorageservice.application.restapis.request.MetaData;
import com.typeface.assignment.filestorageservice.commons.constants.ApiConstants;
import com.typeface.assignment.filestorageservice.commons.constants.CommonConstants;
import com.typeface.assignment.filestorageservice.commons.enums.ResponseEnum;
import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.domain.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class fileUpdateController {


    @Autowired
    private FileService fileService;


    @Autowired
    private ResponseBuilderHelper responseBuilderHelper;

    ObjectMapper objectMapper = new ObjectMapper();


    @PutMapping(ApiConstants.Controller.UPDATE_API)
    @CrossOrigin(origins = "${allowed.cross.origins}")
    public ResponseEntity<String> updateFile(@PathVariable String fileId, @RequestParam("updatedFile") MultipartFile file, @RequestParam("updatedFileName") String fileName, @RequestParam("updatedMetadata") String metadata) {
        try {
            log.info("Request Received for {}, fileId {}", MDC.get(CommonConstants.REST_API),fileId);

            String updatedFileKey = fileService.updateFileInS3(fileId, file);

            if (updatedFileKey != null) {
                MetaData fileMetadata = objectMapper.readValue(metadata, MetaData.class);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                FileMetadata updatedMetadata = new FileMetadata(fileId, fileName, file.getContentType(), dateFormat.parse(fileMetadata.getCreatedTime()), new Date(), file.getSize());
                fileService.deleteFileMetadata(fileId);
                fileService.updateFileMetadata(updatedMetadata);

                // Return updated metadata or a success message
                return ResponseEntity.ok("File updated successfully");
            }

            return responseBuilderHelper.buildFailureResponse(ResponseEnum.TARGET_NOT_FOUND);

        } catch (Throwable throwable) {
            log.error("Error Occurred in update Api ", throwable);
            return responseBuilderHelper.buildFailureResponse(ResponseEnum.SYSTEM_ERROR);
        }
    }


}
