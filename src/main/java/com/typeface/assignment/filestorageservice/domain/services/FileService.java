package com.typeface.assignment.filestorageservice.domain.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.typeface.assignment.filestorageservice.domain.entities.FileMetadata;
import com.typeface.assignment.filestorageservice.infrastructure.repositories.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Value("${aws.s3.bucketName}")
    private String s3BucketName;

    // Upload a file to Amazon S3 and return the file key
    public String uploadFileToS3(MultipartFile file, String fileName) {
        try {
            String fileKey = generateUniqueFileKey(fileName);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(s3BucketName, fileKey, file.getInputStream(), metadata);
            return fileKey;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }

    // Save file metadata to MySQL
    public void saveFileMetadata(FileMetadata metadata) {
        fileMetadataRepository.save(metadata);
    }

    // Retrieve file metadata from MySQL by file ID
    public FileMetadata getFileMetadata(String fileId) {
        return fileMetadataRepository.findByFileId(fileId);
    }

    // Retrieve a file from Amazon S3 by file key
    public Resource getFileFromS3(String fileKey) {
        S3Object object = amazonS3.getObject(s3BucketName, fileKey);
        S3ObjectInputStream objectInputStream = object.getObjectContent();
        return new InputStreamResource(objectInputStream);
    }

    // Update a file in Amazon S3 and return the updated file key
    public String updateFileInS3(String fileId, MultipartFile file) {


        FileMetadata fileMetadata = fileMetadataRepository.findByFileId(fileId);
        if (fileMetadata != null) {
        try {
            if (file != null && !file.isEmpty()) {
                deleteFileFromS3(fileId);
                amazonS3.putObject(new PutObjectRequest(s3BucketName, fileMetadata.getFileName(), file.getInputStream(), null));
            }
            return fileMetadata.getFileId();
        } catch (IOException e) {
            return null;
        }
        }
        return null;
    }

    // Update file metadata in MySQL
    public void updateFileMetadata(FileMetadata updatedMetadata) {
        fileMetadataRepository.save(updatedMetadata);
    }

    // Delete a file from Amazon S3 by file key
    public boolean deleteFileFromS3(String fileKey) {
        try {
            amazonS3.deleteObject(s3BucketName, fileKey);
            return true;
        } catch (AmazonS3Exception e) {
            throw new RuntimeException("Failed to delete file from S3: " + e.getMessage(), e);
        }
    }

    // Delete file metadata from MySQL by file ID
    @Transactional
    public void deleteFileMetadata(String fileId) {
        fileMetadataRepository.deleteByFileId(fileId);
    }

    // Retrieve a list of all file metadata from MySQL
    public List<FileMetadata> getAllFileMetadata() {
        return fileMetadataRepository.findAll();
    }

    // Generating a unique file key based on the original file name and the time at which it is being generated.
    private String generateUniqueFileKey(String fileName) {

        return fileName + System.currentTimeMillis();

    }
}
