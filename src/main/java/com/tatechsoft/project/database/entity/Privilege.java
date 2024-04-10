package com.tatechsoft.project.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UMS_PRIVILEGE", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODE"})})
@Getter
@Setter
public class Privilege {
    @Id
    @Column(name = "CODE")
    private String code;
    @Column(name = "LABEL")
    private String label;
    @Column(name = "GROUP_CODE")
    private String groupCode;
    @Column(name = "GROUP_LABEL")
    private String groupLabel;
    @Column(name = "NO")
    private Integer no;

    public Privilege() {
        super();
    }

    public Privilege(String groupCode, String code, String label, String groupLabel, Integer no) {
        this.code = code;
        this.groupCode = groupCode;
        this.label = label;
        this.groupLabel = groupLabel;
        this.no = no;
    }
}
