package com.tatechsoft.project.database.repository.ums;

import com.tatechsoft.project.common.utils.UserLoginUtils;
import com.tatechsoft.project.database.entity.RolePrivilege;
import com.tatechsoft.project.database.entity.UserPrivilege;
import com.tatechsoft.project.database.entity.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PermissionRepo {

    @PersistenceContext
    EntityManager em;

    public boolean isPermission(String privilegeCode) {

        String username = UserLoginUtils.getUsername();

        //  RolePrivilege
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT pv FROM RolePrivilege pv");
        jpql.append(" , UserRole ur");
        jpql.append(" WHERE pv.role.id = ur.role.id");
        jpql.append(" AND pv.privilege.code = :privilegeCode");
        jpql.append(" AND ur.user.username = :username");
        Query query = em.createQuery(jpql.toString());
        query.setParameter("privilegeCode", privilegeCode);
        query.setParameter("username", username);
        List<RolePrivilege> result = query.getResultList();
        boolean rolePrivilege = ObjectUtils.isNotEmpty(result);

        //  UserPrivilege
        jpql = new StringBuilder();
        jpql.append(" SELECT up FROM UserPrivilege up");
        jpql.append(" WHERE up.privilege.code = :privilegeCode");
        jpql.append(" AND up.user.username = :username");
        query = em.createQuery(jpql.toString());
        query.setParameter("privilegeCode", privilegeCode);
        query.setParameter("username", username);
        List<UserPrivilege> resultUP = query.getResultList();
        boolean userPrivilege = ObjectUtils.isNotEmpty(resultUP);

        return rolePrivilege || userPrivilege;
    }

    public List<String> findPrivilegesByUser(String username) {

        Query query = null;
        StringBuilder jpql = null;

        //  UserRole
        jpql = new StringBuilder();
        jpql.append("SELECT ur FROM UserRole ur  ");
        jpql.append(" WHERE ur.user.username = :username ");
        jpql.append(" AND ur.isDeleted = 'N' ");
        jpql.append(" AND ur.role.isDeleted = 'N' ");
        jpql.append(" AND ur.user.isDeleted = 'N' ");
        query = em.createQuery(jpql.toString());
        query.setParameter("username", username);
        List<UserRole> userRoleList = query.getResultList();

        List<String> roleList = new ArrayList<>();
        for (UserRole ru : userRoleList) {
            roleList.add(ru.getRole().getCode());
        }

        //  RolePrivilege
        jpql = new StringBuilder();
        jpql.append(" SELECT pv FROM RolePrivilege pv ");
        jpql.append(" WHERE pv.role.id IN (:roleList) ");
        jpql.append(" AND pv.isDeleted = 'N' ");
        query = em.createQuery(jpql.toString());
        query.setParameter("roleList", roleList);
        List<RolePrivilege> rolePrivilegeList = query.getResultList();

        List<String> privilegeList = new ArrayList<>();
        for (RolePrivilege rp : rolePrivilegeList) {
            privilegeList.add(rp.getPrivilege().getCode());
        }

        //  UserPrivilege
        jpql = new StringBuilder();
        jpql.append(" SELECT up FROM UserPrivilege up ");
        jpql.append(" WHERE up.user.username = :username ");
        jpql.append(" AND up.isDeleted = 'N' ");
        jpql.append(" AND up.user.isDeleted = 'N'");
        query = em.createQuery(jpql.toString());
        query.setParameter("username", username);
        List<UserPrivilege> resultUP = query.getResultList();

        for (UserPrivilege rs : resultUP) {
            privilegeList.add(rs.getPrivilege().getCode());
        }

        return privilegeList.stream().distinct().collect(Collectors.toList());
    }

}
