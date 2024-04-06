package com.example.demo.bounded_context.separation.repository;

import com.example.demo.bounded_context.category.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Optional<MainCategory> findByCategoryName(String categoryName);
}
