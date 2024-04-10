package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.module.ums.criteria.UserCriteria;

public interface UserRepoCustom {
    DataTable<User> searchUser(UserCriteria criteria);
    User findUserByEmail(String email);
}