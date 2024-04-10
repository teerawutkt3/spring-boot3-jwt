package com.tatechsoft.project.common.utils;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.common.criteria.BaseCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CriteriaQueryUtils<T> {

    @PersistenceContext
    EntityManager em;

    public List<T> createQuery(EntityManager em, CriteriaQuery<T> cq, BaseCriteria criteria) {
        return em.createQuery(cq)
                .setFirstResult((criteria.getPage() - 1) * criteria.getPageSize()) // offset
                .setMaxResults(criteria.getPageSize()).getResultList(); // limit
    }

    public DataTable<T> createQuery(EntityManager em, StringBuilder jpql, BaseCriteria criteria, Map<String, Object> params) {
        //  Query Data
        Query query = em.createQuery(jpql.toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        List data = queryPagination(query, criteria);

        //  Query Add String Count(x)
        String[] jpqlSplit = jpql.toString().split("FROM");
        String[] rsSplit = jpqlSplit[0].split(" ");
        String[] jpqlSplitForCount = jpqlSplit[1].split("ORDER");
        String jpqlCount = rsSplit[0] + " COUNT(" + rsSplit[1] + ") FROM " + jpqlSplitForCount[0];

        //  Query Count
        query = em.createQuery(jpqlCount);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        Long count = (Long) query.getSingleResult();
        //  Set Result DataTableBean
        DataTable<T> dataTable = new DataTable<>();
        dataTable.setData(data);
        dataTable.setTotal(count);
        dataTable.setSort(criteria.getSort());
        dataTable.setSortDirection(criteria.getSortDirection());
        dataTable.setPage(criteria.getPage());
        dataTable.setPageSize(criteria.getPageSize());
        return dataTable;
    }

    private List queryPagination(Query query, BaseCriteria criteria) {
        return query.setFirstResult((criteria.getPage() - 1) * criteria.getPageSize()) // offset
                .setMaxResults(criteria.getPageSize()).getResultList(); // limit
    }

}
