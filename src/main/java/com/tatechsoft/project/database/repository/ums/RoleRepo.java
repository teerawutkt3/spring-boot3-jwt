package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.database.entity.Role;
import com.tatechsoft.project.database.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepo extends JpaRepository<Role, String> {
    List<Role> findAllByOrderByNoAsc();

    UserRole save(UserRole userRole);
}
