package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.common.repository.CommonJpaCrudRepository;
import com.tatechsoft.project.database.entity.User;

public interface UserRepo extends CommonJpaCrudRepository<User, Long>, UserRepoCustom {
    User findByUsername(String username);
}
