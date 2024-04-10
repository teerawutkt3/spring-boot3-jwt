package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.common.utils.CriteriaQueryUtils;
import com.tatechsoft.project.database.entity.SubDistrict;
import com.tatechsoft.project.module.geo.criteria.SubdistrictCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class SubDistrictRepoImpl implements SubDistrictRepoCustom {

    @PersistenceContext
    EntityManager em;

    public DataTable<SubDistrict> searchSubDistrict(SubdistrictCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder jpql = new StringBuilder("SELECT s FROM SubDistrict s WHERE 1=1 ");

        //  Order By
        if (ObjectUtils.isNotEmpty(criteria.getSort())) {
            jpql.append(" ORDER BY s." + criteria.getSort() + " " + criteria.getSortDirection());
        } else {
            jpql.append(" ORDER BY s.createdDate DESC");
        }

        return new CriteriaQueryUtils<SubDistrict>().createQuery(em, jpql, criteria, params);
    }
}