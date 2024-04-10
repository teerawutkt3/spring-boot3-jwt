package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.common.utils.CriteriaQueryUtils;
import com.tatechsoft.project.database.entity.Province;
import com.tatechsoft.project.module.geo.criteria.ProvinceCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.Map;

public class ProvinceRepoImpl implements ProvinceRepoCustom {

    @PersistenceContext
    EntityManager em;

    public DataTable<Province> searchProvince(ProvinceCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder jpql = new StringBuilder("SELECT p FROM Province p WHERE 1=1 ");
        //  Order By
        jpql.append(" ORDER BY p.nameTh ASC");

        return new CriteriaQueryUtils<Province>().createQuery(em, jpql, criteria, params);
    }
}