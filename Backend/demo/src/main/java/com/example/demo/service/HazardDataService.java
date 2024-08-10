package com.example.demo.service;

import com.example.demo.entity.HazardData;
import com.example.demo.repository.HazardDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HazardDataService {

    private final HazardDataRepository hazardDataRepository;

    public HazardDataService(HazardDataRepository hazardDataRepository) {
        this.hazardDataRepository = hazardDataRepository;
    }

    public List<HazardData> getAllHazardData() {
        return hazardDataRepository.findAll();
    }

    public HazardData saveHazardData(HazardData hazardData) {
        return hazardDataRepository.save(hazardData);
    }

    public void deleteHazardDataByHid(Long hid) {
        hazardDataRepository.deleteById(hid); // id 대신 hid를 사용해야 하는 경우 직접 구현
    }

}
