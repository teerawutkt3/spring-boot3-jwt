package com.tatechsoft.project.database.repository.geo;

import com.tatechsoft.project.database.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepo extends JpaRepository<Province, Integer>, ProvinceRepoCustom {

    List<Province> findAllByOrderByNameTh();
}