package com.tatechsoft.project.module.geo.service;

import com.tatechsoft.project.database.entity.District;
import com.tatechsoft.project.database.entity.SubDistrict;
import com.tatechsoft.project.database.repository.geo.SubDistrictRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubDistrictService {

    @Autowired
    private SubDistrictRepo subDistrictRepo;

    public List<SubDistrict> findSubDistrictByDistrict(Integer districtCode) {
        District district = new District();
        district.setCode(districtCode);
        return subDistrictRepo.findByDistrictOrderByNameTh(district);
    }
}
