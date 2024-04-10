package com.tatechsoft.project.database.entity;

import com.tatechsoft.project.common.entity.BaseNoIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UMS_ROLE", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODE"})})
@Getter
@Setter
public class Role extends BaseNoIdEntity {
    @Id
    @Column(name = "CODE")
    private String code;
    @Column(name = "LABEL")
    private String label;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "NO")
    private Integer no;

    public Role() {
        super();
    }

    public Role(String code, String label, String description, Integer no) {
        this.code = code;
        this.label = label;
        this.description = description;
        this.isDeleted = "N";
        this.no = no;
    }
}
