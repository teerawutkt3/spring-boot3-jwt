package com.tatechsoft.project.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tatechsoft.project.common.entity.BaseNoIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "UMS_ROLE_PRIVILEGE")
@Getter
@Setter
public class RolePrivilege extends BaseNoIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIVILEGE_CODE")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Privilege privilege;

    public RolePrivilege() {
        super();
    }

    public RolePrivilege(Role role, Privilege privilege) {
        this.role = role;
        this.privilege = privilege;
    }
}

