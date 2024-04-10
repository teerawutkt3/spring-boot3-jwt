package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.common.model.DataTable;
import com.tatechsoft.project.database.entity.SubDistrict;
import com.tatechsoft.project.module.geo.criteria.SubdistrictCriteria;

public interface SubDistrictRepoCustom {
    DataTable<SubDistrict> searchSubDistrict(SubdistrictCriteria criteria);
}