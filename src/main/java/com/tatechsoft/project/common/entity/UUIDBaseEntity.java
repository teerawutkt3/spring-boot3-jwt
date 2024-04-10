package com.tatechsoft.project.common.entity;

import com.tatechsoft.project.common.utils.DateUtils;
import com.tatechsoft.project.common.utils.UserLoginUtils;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class UUIDBaseEntity implements Serializable {

    private static final long serialVersionUID = 5140575813889967178L;

    @Id
//    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "IS_DELETED", length = 1, nullable = false)
    protected String isDeleted;


    @Column(name = "CREATED_BY", updatable = false)
    protected String createdBy;

    @Column(name = "CREATED_DATE", updatable = false)
    protected Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true)
    protected String updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    protected Date updatedDate;

    @PrePersist
    public void prePersist() {
        if (!"Y".equals(isDeleted))
            isDeleted = "N";
        else
            isDeleted = "Y";

        if (StringUtils.isBlank(createdBy)) {
            createdBy = UserLoginUtils.getUsername();
        }

        if (StringUtils.isBlank(updatedBy)) {
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

    public String getIdStr() {
        return id.toString();
    }

    public boolean isNew() {
        return ObjectUtils.isEmpty(this.id);
    }
}
