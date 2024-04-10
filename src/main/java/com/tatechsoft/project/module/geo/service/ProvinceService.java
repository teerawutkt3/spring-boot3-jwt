package com.tatechsoft.project.module.geo.service;

import com.tatechsoft.project.database.entity.Province;
import com.tatechsoft.project.database.repository.geo.ProvinceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepo provinceRepo;

    public List<Province> findAll() {
        return provinceRepo.findAllByOrderByNameTh();
    }
}