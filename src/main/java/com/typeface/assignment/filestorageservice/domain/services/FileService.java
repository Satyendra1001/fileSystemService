package com.typeface.assignment.filestorageservice.domain.services;

import com.amazonaws.services.s3.model.*;
import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

public interface FileService {
     String uploadFileToS3(MultipartFile file, String fileName);

     void saveFileMetadata(FileMetadata metadata);

     FileMetadata getFileMetadata(String fileId) ;

     Resource getFileFromS3(String fileKey) ;

     String updateFileInS3(String fileId, MultipartFile file) ;


     void updateFileMetadata(FileMetadata updatedMetadata) ;

     boolean deleteFileFromS3(String fileKey) ;

    @Transactional
     void deleteFileMetadata(String fileId) ;

    public FileMetadata findFileMetadata(String fileId) ;
     List<FileMetadata> getAllFileMetadata() ;
     String generateUniqueFileKey(String fileName) ;
}
