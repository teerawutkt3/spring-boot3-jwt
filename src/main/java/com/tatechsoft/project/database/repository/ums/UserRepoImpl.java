package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.common.utils.CriteriaQueryUtils;
import com.tatechsoft.project.database.entity.User;
import com.tatechsoft.project.module.ums.criteria.UserCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserRepoImpl implements UserRepoCustom {

    @PersistenceContext
    EntityManager em;

    public DataTable<User> searchUser(UserCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder jpql = new StringBuilder("SELECT u FROM User u WHERE 1=1 ");

        if (StringUtils.isNotEmpty(criteria.getUsername())) {
            jpql.append(" AND u.username = :username");
            params.put("username", criteria.likeField(criteria.getUsername()));
        }

        if (StringUtils.isNotEmpty(criteria.getSearch())) {
            jpql.append(" AND (u.username like :param OR u.isDeleted like :param OR u.tel like : param)");
            params.put("param", "%" + criteria.getSearch() + "%");
        }

        jpql.append(" AND u.isDeleted <> :isDeleted");
        params.put("isDeleted", "Y");

        //  Order By
        if (StringUtils.isNotEmpty(criteria.getSort())) {
            jpql.append(" ORDER BY u." + criteria.getSort() + " " + criteria.getSortDirection());
        } else {
            jpql.append(" ORDER BY u.createdDate DESC");
        }

        return new CriteriaQueryUtils<User>().createQuery(em, jpql, criteria, params);
    }

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("123456");
        return user;
    }
}