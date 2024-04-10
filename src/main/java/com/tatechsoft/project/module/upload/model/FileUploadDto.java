package com.tatechsoft.project.module.upload.model;


import lombok.Data;

@Data
public class FileUploadDto {
    private Long uploadId;
    private String fileName;
    private String displayName;

}
