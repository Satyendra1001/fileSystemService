package com.typeface.assignment.filestorageservice.application.helpers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typeface.assignment.filestorageservice.application.restapis.response.ResponseInfo;
import com.typeface.assignment.filestorageservice.application.restapis.response.UploadResponse;
import com.typeface.assignment.filestorageservice.commons.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseBuilderHelper {

    ObjectMapper objectMapper = new ObjectMapper();


    public ResponseEntity<String> buildSuccessResponseWithFileId(String fieldId){

        ResponseInfo responseInfo = new ResponseInfo(ResponseEnum.SUCCESS);

        UploadResponse uploadResponse = new UploadResponse();
        uploadResponse.setResponseInfo(responseInfo);
        uploadResponse.setFileId(fieldId);
        String responseJson = null;
        try {
            responseJson = objectMapper.writeValueAsString(uploadResponse);
        } catch (JsonProcessingException e) {
            return buildFailureResponse(ResponseEnum.SYSTEM_ERROR);
        }

        return ResponseEntity.ok(responseJson);


    }

    public ResponseEntity<String> buildCommonSuccessResponse(){

        ResponseInfo responseInfo = new ResponseInfo(ResponseEnum.SUCCESS);
        String responseJson = null;
        try {
            responseJson = objectMapper.writeValueAsString(responseInfo);
        } catch (JsonProcessingException e) {
            return buildFailureResponse(ResponseEnum.SYSTEM_ERROR);
        }

        return ResponseEntity.ok(responseJson);


    }

    public ResponseEntity<String> buildFailureResponse(ResponseEnum responseEnum){

        ResponseInfo responseInfo = new ResponseInfo(responseEnum);
        String responseJson = null;
        try {
            responseJson = objectMapper.writeValueAsString(responseInfo);
        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(responseJson);


    }





}
