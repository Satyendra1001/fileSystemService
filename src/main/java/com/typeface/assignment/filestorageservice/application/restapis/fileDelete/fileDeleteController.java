package com.typeface.assignment.filestorageservice.application.restapis.fileDelete;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class fileDeleteController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ResponseBuilderHelper responseBuilderHelper;

    @DeleteMapping(ApiConstants.Controller.DELETE_API)
    @CrossOrigin(origins = "${allowed.cross.origins}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        log.info("Request Received for {}, fileId {}", MDC.get(CommonConstants.REST_API),fileId);

        FileMetadata fileMetadata = fileService.findFileMetadata(fileId);

        if (fileMetadata == null) {
            return responseBuilderHelper.buildFailureResponse(ResponseEnum.TARGET_NOT_FOUND);
        }
        // Delete the file from Amazon S3
        boolean deleted = fileService.deleteFileFromS3(fileMetadata.getFileId());

        if (deleted) {
            fileService.deleteFileMetadata(fileId);
            log.info("File deleted successfully, fileId :{}",fileId);
            return responseBuilderHelper.buildCommonSuccessResponse();
        }

        return responseBuilderHelper.buildFailureResponse(ResponseEnum.SYSTEM_ERROR);
    }

}
