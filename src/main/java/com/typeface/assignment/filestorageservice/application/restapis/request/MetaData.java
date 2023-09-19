package com.typeface.assignment.filestorageservice.application.restapis.request;

import lombok.Data;

@Data
public class MetaData {
    private String  fileType;
    private String  size;
    private String createdTime;
}
