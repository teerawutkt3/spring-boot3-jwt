package com.tatechsoft.project.module.ums.model;

import com.tatechsoft.project.database.entity.Privilege;
import lombok.Data;

import java.util.List;

@Data
public class PrivilegeGroupDto {
    private String group;
    private String groupDesc;
    private List<Privilege> privileges;
}
