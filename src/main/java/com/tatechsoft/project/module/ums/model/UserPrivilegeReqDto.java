package com.tatechsoft.project.module.ums.model;

import lombok.Data;

import java.util.List;

@Data
public class UserPrivilegeReqDto {
    private Long userId;
    private List<String> privileges;
}
