package com.typeface.assignment.filestorageservice.application.restapis.response;

import com.typeface.assignment.filestorageservice.commons.enums.ResponseEnum;
import lombok.Data;

@Data
public class ResponseInfo {

    private String responseStatus;
    private String responseCode;
    private String responseMsg;

    public ResponseInfo(ResponseEnum responseEnum){
        this.responseStatus = responseEnum.getResponseStatus();
        this.responseCode = responseEnum.getResponseCode();
        this.responseMsg = responseEnum.getResponseMsg();
    }

}
