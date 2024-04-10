package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.database.entity.District;
import com.tatechsoft.project.database.entity.SubDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubDistrictRepo extends JpaRepository<SubDistrict, Integer>, SubDistrictRepoCustom {
    List<SubDistrict> findByDistrictOrderByNameTh(District district);
}