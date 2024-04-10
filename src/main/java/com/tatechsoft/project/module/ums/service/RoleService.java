package com.tatechsoft.project.module.ums.service;

import com.tatechsoft.project.database.entity.Role;
import com.tatechsoft.project.database.repository.ums.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Transactional
    public Role saveRole(Role role) {
        //  Save role && privilege[]
        return roleRepo.save(role);
    }
}
