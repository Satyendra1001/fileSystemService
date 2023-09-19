package com.typeface.assignment.filestorageservice.application.restapis.fileUpload;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.typeface.assignment.filestorageservice.application.restapis.request.MetaData;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class fileUploadController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ResponseBuilderHelper responseBuilderHelper;

    ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping(ApiConstants.Controller.UPLOAD_API)
    @CrossOrigin(origins = "${allowed.cross.origins}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName, @RequestParam("metadata") String metadata) {
        try {
            log.info("Request Received for {}, fileName {}", MDC.get(CommonConstants.REST_API),fileName);
            String fileKey = fileService.uploadFileToS3(file, fileName);
            MetaData fileMetadata = objectMapper.readValue(metadata, MetaData.class);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
            FileMetadata filemetadata = new FileMetadata(fileKey, fileName,file.getContentType(), dateFormat.parse(fileMetadata.getCreatedTime()),new Date(), file.getSize());

            fileService.saveFileMetadata(filemetadata);
            return responseBuilderHelper.buildSuccessResponseWithFileId(fileKey);
        } catch (Throwable throwable){
            log.error("Error Occurred in upload Api ", throwable);
            return responseBuilderHelper.buildFailureResponse(ResponseEnum.SYSTEM_ERROR);
        }
    }
}
