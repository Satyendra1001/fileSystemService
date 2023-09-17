package com.typeface.assignment.filestorageservice.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="file_meta_data")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileId;
    private String fileName;
    private String contentType;
    private Date createdAt;
    private Date modifiedAt;
    private Long fileSize;

    public FileMetadata(String fileId, String fileName, String contentType, Date createdAt,Date modifiedAt, Long fileSize) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.fileSize = fileSize;
    }

    public FileMetadata() {
    }

}
