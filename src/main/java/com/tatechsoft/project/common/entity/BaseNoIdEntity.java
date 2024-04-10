package com.tatechsoft.project.common.entity;


import com.tatechsoft.project.common.utils.DateUtils;
import com.tatechsoft.project.common.utils.UserLoginUtils;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseNoIdEntity implements Serializable {

    @Column(name = "IS_DELETED", length = 1, nullable = false)
    protected String isDeleted;


    @Column(name = "CREATED_BY", updatable = false)
    protected String createdBy;

    @Column(name = "CREATED_DATE", updatable = false)
    protected Date createdDate;

    @Column(name = "UPDATED_BY")
    protected String updatedBy;

    @Column(name = "UPDATED_DATE")
    protected Date updatedDate;

    @PrePersist
    public void prePersist() {
        if (!"Y".equals(isDeleted))
            isDeleted = "N";
        else
            isDeleted = "Y";

        if (StringUtils.isEmpty(createdBy)) {
            createdBy = UserLoginUtils.getUsername();
        }

        if (StringUtils.isEmpty(updatedBy)) {
            updatedBy = UserLoginUtils.getUsername();
        }

        createdDate = new Date();
        updatedDate = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedBy = UserLoginUtils.getUsername();
        updatedDate = new Date();
    }

    public String getCreatedDateStr() {
        return DateUtils.formatDate(createdDate, DateUtils.DD_MM_YYYY_HHMMSS);
    }

    public String getUpdatedDateStr() {
        return DateUtils.formatDate(updatedDate, DateUtils.DD_MM_YYYY_HHMMSS);
    }
}
