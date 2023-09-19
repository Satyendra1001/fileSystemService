package com.typeface.assignment.filestorageservice.commons.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
public enum ResponseEnum {

    SUCCESS("SUCCESS","FS0000100","Request Processed Successfully"),
    TARGET_NOT_FOUND("FAILURE","FS0000200","Target Not Found"),
    BAD_REQUEST("FAILURE","FS0000201","Bad Request"),
    SYSTEM_ERROR("FAILURE","FS0000300","Something Went Wrong");



    private final String responseStatus;
    private final String responseCode;
    private final String responseMsg;

    ResponseEnum(String responseStatus, String responseCode, String responseMsg){
        this.responseCode = responseCode;
        this.responseStatus = responseStatus;
        this.responseMsg = responseMsg;
    }

}
