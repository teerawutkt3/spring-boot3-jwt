package com.tatechsoft.project.module.ums.criteria;

import com.tatechsoft.project.common.criteria.BaseCriteria;
import lombok.Data;

@Data
public class UserCriteria extends BaseCriteria {
    private String username;
    private String search;
}
