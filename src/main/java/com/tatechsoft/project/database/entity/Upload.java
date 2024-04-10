package com.tatechsoft.project.database.entity;

import com.tatechsoft.project.common.entity.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "UPLOAD")
@Getter
@Setter
@ToString
@AttributeOverride(name = "id", column = @Column(name = "UPLOAD_ID", nullable = false))
public class Upload extends BaseEntity {

    @Column(name = "MODULE_NAME")
    private String moduleName;

    @Column(name = "FILENAME", length = 500)
    private String filename;

    @Column(name = "DISPLAY_NAME", length = 300)
    private String displayName;

    @Column(name = "SIZE")
    private Double size;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "PATH")
    private String path;

    @Column(name = "TABLE_PARENT")
    private String tableParent;

    @Column(name = "ID_PARENT")
    private String idParent;
}
