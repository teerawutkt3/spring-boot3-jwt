package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.database.entity.District;
import com.tatechsoft.project.module.geo.criteria.DistrictCriteria;

public interface DistrictRepoCustom {
    DataTable<District> searchDistrict(DistrictCriteria criteria);

    District findByProvinceId(int provinceId);
}