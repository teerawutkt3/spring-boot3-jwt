package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.database.entity.Role;
import com.tatechsoft.project.database.entity.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePrivilegeRepo extends JpaRepository<RolePrivilege, Long> {

    List<RolePrivilege> findByRole(Role role);

    void deleteByRole(Role role);
}
