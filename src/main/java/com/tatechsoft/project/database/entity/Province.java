package com.tatechsoft.project.database.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GEO_PROVINCES")
@Getter
@Setter
public class Province {

    @Id
    @Column(name = "CODE", nullable = false, unique = true)
    private Integer code;

    @Column(name = "NAME_TH", length = 150, nullable = false)
    private String nameTh;

    @Column(name = "NAME_EN", length = 150, nullable = false)
    private String nameEn;
}