package com.example.demo.bounded_context.category.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MiddleCategory_ID", nullable = false)
    private MiddleCategory middleCategoryId;

    @Builder
    private SubCategory(String categoryName, MiddleCategory middleCategoryId){
        this.categoryName=categoryName;
        this.middleCategoryId=middleCategoryId;
    }
}
