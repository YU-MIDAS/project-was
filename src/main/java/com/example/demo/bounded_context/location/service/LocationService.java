package com.example.demo.bounded_context.location.service;

import com.example.demo.bounded_context.location.dto.LocationResponse;
import com.example.demo.bounded_context.location.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final MedicineRepository medicineRepository;

    public List<LocationResponse> findMedicine (Double latitude, Double longitude, Double radius){
        return medicineRepository.findByCoordinate(latitude, longitude, radius)
                .stream().map(medicine -> medicine.of())
                .collect(Collectors.toList());
    }

    public List<LocationResponse> findMedicine (String state, String city){
        return medicineRepository.findByAddress(state, city);
    }
}
