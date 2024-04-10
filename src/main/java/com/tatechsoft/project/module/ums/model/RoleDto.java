package com.tatechsoft.project.module.ums.model;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private long id;
    private String code;
    private String name;
    private String description;
    private List<PrivilegeDto> privileges;
}
