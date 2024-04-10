package com.tatechsoft.project.module.upload.model;

import lombok.Data;

@Data
public class UploadFileDto {
    private String path;
    private Long size;
    private String type;
    private String name;
}
