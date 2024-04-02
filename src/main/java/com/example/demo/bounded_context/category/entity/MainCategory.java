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
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    private String solution;

    @OneToOne
    @JoinColumn(name = "IMAGE_ID", nullable = false)
    private Image imageId;

    @Builder
    public MainCategory(String categoryName, String solution, Image imageId){
        this.categoryName =categoryName;
        this.solution = solution;
        this.imageId = imageId;
    }

}
