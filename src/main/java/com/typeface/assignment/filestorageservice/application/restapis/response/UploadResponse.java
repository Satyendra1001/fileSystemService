package com.typeface.assignment.filestorageservice.application.restapis.response;

import lombok.Data;

@Data
public class UploadResponse {
    String fileId;
    ResponseInfo responseInfo;
}
