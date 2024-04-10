package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.database.entity.District;
import com.tatechsoft.project.database.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepo extends JpaRepository<District, Integer>, DistrictRepoCustom {

    List<District> findByProvinceOrderByNameTh(Province province);
}