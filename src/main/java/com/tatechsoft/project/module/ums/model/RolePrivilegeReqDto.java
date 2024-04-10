package com.tatechsoft.project.module.ums.model;

import lombok.Data;

import java.util.List;

@Data
public class RolePrivilegeReqDto {
    private String roleCode;
    private List<String> privileges;
}
