package com.example.demo.bounded_context.solution.repository;

import com.example.demo.bounded_context.solution.entity.Material;
import com.example.demo.bounded_context.solution.entity.Waste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query("select w from Material m join m.waste w where w.name = :name")
    List<Waste> findByMaterName(String name);
}
