package com.tatechsoft.project.module.geo.service;

import com.tatechsoft.project.database.entity.District;
import com.tatechsoft.project.database.entity.Province;
import com.tatechsoft.project.database.repository.geo.DistrictRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepo districtRepo;

    public List<District> findDistrict(int provinceCode) {
        Province province = new Province();
        province.setCode(provinceCode);
        return districtRepo.findByProvinceOrderByNameTh(province);
    }

    public List<District> getDistrictByProvinceId(Province province) {
        return districtRepo.findByProvinceOrderByNameTh(province);
    }

    public District getDistrictById(int id) {
        return districtRepo.findById(id).get();
    }
}