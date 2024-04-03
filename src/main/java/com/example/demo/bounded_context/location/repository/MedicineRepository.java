package com.example.demo.bounded_context.location.repository;

import com.example.demo.bounded_context.location.dto.LocationResponse;
import com.example.demo.bounded_context.location.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    @Query(value = "SELECT * FROM medicine m WHERE ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(m.longitude, m.latitude)) <= :radius", nativeQuery = true)
    List<Medicine> findByCoordinate(Double latitude, Double longitude, Double radius);

    @Query(value = "SELECT new com.example.demo.bounded_context.location.dto.LocationResponse(m.address, m.latitude, m.longitude) FROM Medicine m WHERE m.state = :state AND m.city = :city")
    List<LocationResponse> findByAddress(String state, String city);
}
