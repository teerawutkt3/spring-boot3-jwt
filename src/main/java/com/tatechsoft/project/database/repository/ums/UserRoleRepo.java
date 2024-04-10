package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.database.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);

    void deleteByUser(User user);
}
