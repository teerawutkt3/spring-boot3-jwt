package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.database.entity.Province;
import com.tatechsoft.project.module.geo.criteria.ProvinceCriteria;

public interface ProvinceRepoCustom {
    DataTable<Province> searchProvince(ProvinceCriteria criteria);
}