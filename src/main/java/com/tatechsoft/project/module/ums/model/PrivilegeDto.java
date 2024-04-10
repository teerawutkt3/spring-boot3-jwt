package com.tatechsoft.project.module.ums.model;

import lombok.Data;

@Data
public class PrivilegeDto {

    private boolean canCreate = false;
    private boolean canUpdate = false;
    private boolean canDelete = false;
    private boolean canApprove = false;
    private boolean canReject = false;
    private boolean canExport = false;
    private boolean canProcess = false;
    private String code;
    private String path;
}


