package com.example.demo.bounded_context.location.repository;

import com.example.demo.bounded_context.location.dto.LocationResponse;
import com.example.demo.bounded_context.location.entity.LampAndBattery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LampAndBatteryRepository extends JpaRepository<LampAndBattery, Long> {
    @Query(value = "SELECT * FROM lamp_and_battery lb WHERE ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(lb.longitude, lb.latitude)) <= :radius", nativeQuery = true)
    List<LampAndBattery> findByCoordinate(Double latitude, Double longitude, Double radius);

    @Query(value = "SELECT new com.example.demo.bounded_context.location.dto.LocationResponse(lb.address, lb.latitude, lb.longitude) FROM LampAndBattery lb WHERE lb.state = :state AND lb.city = :city")
    List<LocationResponse> findByAddress(String state, String city);
}
