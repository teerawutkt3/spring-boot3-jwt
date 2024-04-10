package com.tatechsoft.project.common.model;


import com.tatechsoft.project.common.criteria.BaseCriteria;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class DataTable<T> {
    private List<T> data;
    private long total = 0l;
    private int page = 1;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sort;

    public DataTable(List<T> data, long total) {
        this.data = data;
        this.total = total;
    }

    public DataTable(List<T> data, long total, BaseCriteria criteria) {
        this.total = total;
        this.data = data;
        this.page = criteria.getPage();
        this.pageSize = criteria.getPageSize();
        this.sortDirection = criteria.getSortDirection();
        this.sort = criteria.getSort();
    }

    public DataTable() {
        super();
    }


}

