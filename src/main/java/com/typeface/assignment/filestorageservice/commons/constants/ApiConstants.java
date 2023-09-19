package com.typeface.assignment.filestorageservice.commons.constants;

public interface ApiConstants {

    String API_PREFIX = "/files";


    interface Controller{
        String UPLOAD_API = API_PREFIX+ "/upload";
        String READ_API = API_PREFIX+ "/{fileId}";
        String DELETE_API = API_PREFIX+ "/{fileId}";
        String UPDATE_API = API_PREFIX+ "/update/{fileId}";
        String LIST_API = API_PREFIX+ "/list";
    }
}
