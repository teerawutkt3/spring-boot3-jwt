package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.database.entity.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrivilegeRepo extends JpaRepository<UserPrivilege, Long> {

    void deleteByUser(User user);
}
