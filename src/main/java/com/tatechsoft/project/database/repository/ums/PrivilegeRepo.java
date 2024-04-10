package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.database.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepo extends JpaRepository<Privilege, String> {

    List<Privilege> findAllByOrderByNoAsc();
}
