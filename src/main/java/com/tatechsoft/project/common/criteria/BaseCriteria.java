package com.tatechsoft.project.common.criteria;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class BaseCriteria {
    private int page = 1;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sort;

    public String likeField(String param) {
        return "%" + param + "%";
    }
}
