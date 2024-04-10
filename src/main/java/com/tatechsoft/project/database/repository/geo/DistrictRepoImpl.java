package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.common.utils.CriteriaQueryUtils;
import com.tatechsoft.project.database.entity.District;
import com.tatechsoft.project.module.geo.criteria.DistrictCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistrictRepoImpl implements DistrictRepoCustom {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DataTable<District> searchDistrict(DistrictCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder jpql = new StringBuilder("SELECT d FROM District d WHERE 1=1 ");

        jpql.append(" ORDER BY d.nameTh ASC");

        return new CriteriaQueryUtils<District>().createQuery(em, jpql, criteria, params);
    }

    @Override
    public District findByProvinceId(int provinceId) {
        String jpql = "SELECT d FROM District d WHERE d.province.id=?";
        List<Object> params = new ArrayList<>();
        params.add(provinceId);
        Query result = jdbcTemplate.queryForObject(jpql, params.toArray(), new BeanPropertyRowMapper<>());
        return (District) result.getResultList();
    }
}