package com.tatechsoft.project.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GEO_DISTRICTS")
@Getter
@Setter
public class District {

    @Id
    @Column(name = "CODE", nullable = false, unique = true)
    private Integer code;

    @Column(name = "NAME_TH", length = 150, nullable = false)
    private String nameTh;

    @Column(name = "NAME_EN", length = 150, nullable = false)
    private String nameEn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROVINCE_CODE")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Province province;
}