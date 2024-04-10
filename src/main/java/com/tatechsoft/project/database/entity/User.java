package com.tatechsoft.project.database.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tatechsoft.project.common.entity.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "UMS_USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME"})})
@Getter
@Setter
@AttributeOverride(name = "ID", column = @Column(name = "ID", nullable = false))
public class User extends BaseEntity {

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSW0RD", nullable = false)
    private String password;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<String> roles = new ArrayList<>();

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<String> privileges = new ArrayList<>();
}
