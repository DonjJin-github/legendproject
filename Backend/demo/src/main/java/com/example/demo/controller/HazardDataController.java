package com.example.demo.controller;

import com.example.demo.entity.HazardData;
import com.example.demo.service.HazardDataService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/hazarddata")
public class HazardDataController {

    private final HazardDataService hazardDataService;

    public HazardDataController(HazardDataService hazardDataService) {
        this.hazardDataService = hazardDataService;
    }

    @GetMapping
    public List<HazardData> getAllHazardData() {
        return hazardDataService.getAllHazardData();
    }

    @PostMapping("add")
    public String addHazard(@RequestBody HazardData hazardData) {
        hazardDataService.saveHazardData(hazardData);
        return "Hazard data added successfully!";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("delete/{hid}")
    public String deleteHazard(@PathVariable Long hid) {
        // 객체의 ID를 이용해 삭제 수행
        hazardDataService.deleteHazardDataByHid(hid);
        return "Hazard data deleted successfully!";
    }
}
