package com.example.demo.bounded_context.category.entity;

import com.example.demo.bounded_context.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MiddleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    private String solution;

    @OneToOne
    @JoinColumn(name = "IMAGE_ID", nullable = false)
    private Image imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MainCategory_ID", nullable = false)
    private MainCategory mainCategoryId;

    @Builder
    private MiddleCategory(String categoryName, String solution, Image imageId, MainCategory mainCategoryId){
        this.categoryName=categoryName;
        this.solution=solution;
        this.imageId=imageId;
        this.mainCategoryId=mainCategoryId;
    }

}
