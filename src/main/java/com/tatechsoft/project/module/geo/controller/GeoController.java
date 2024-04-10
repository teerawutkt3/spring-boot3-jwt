package com.tatechsoft.project.module.geo.controller;

import com.tatechsoft.project.common.controller.BaseController;
import com.tatechsoft.project.module.geo.service.DistrictService;
import com.tatechsoft.project.module.geo.service.ProvinceService;
import com.tatechsoft.project.module.geo.service.SubDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/geo")
public class GeoController extends BaseController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private SubDistrictService subDistrictService;

    @GetMapping("/provinces")
    public ResponseEntity<?> findProvinceAll() {
        return ResponseEntity.ok(provinceService.findAll());
    }

    @GetMapping("/district/province/{provinceCode}")
    public ResponseEntity<?> findDistrict(@PathVariable int provinceCode) {
        return ResponseEntity.ok(districtService.findDistrict(provinceCode));
    }

    @GetMapping("/sub-district/district//{districtCode}")
    public ResponseEntity<?> getDistrictById(@PathVariable int districtCode) {
        return ResponseEntity.ok(subDistrictService.findSubDistrictByDistrict(districtCode));
    }
}