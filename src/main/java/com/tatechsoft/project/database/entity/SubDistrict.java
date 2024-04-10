package com.tatechsoft.project.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "GEO_SUB_DISTRICTS")
@Getter
@Setter
public class SubDistrict {

    @Id
    @Column(name = "CODE", nullable = false, unique = true)
    private Integer code;

    @Column(name = "NAME_TH", length = 150, nullable = false)
    private String nameTh;

    @Column(name = "NAME_EN", length = 150)
    private String nameEn;

    @Column(name = "LATITUDE", precision = 6, scale = 3)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 6, scale = 3)
    private BigDecimal longitude;

    @Column(name = "ZIP_CODE")
    private Integer zipCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DISTRICT_CODE")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private District district;
}