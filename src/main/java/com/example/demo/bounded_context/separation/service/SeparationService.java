package com.example.demo.bounded_context.separation.service;

import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.bounded_context.category.entity.MainCategory;

import com.example.demo.bounded_context.separation.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeparationService {

    private final MainCategoryRepository mainCategoryRepository;

    public MainCategory read(String name){
        return mainCategoryRepository.findByCategoryName(name).orElseThrow(() -> new CustomException(ExceptionCode.EMPTY_SOLUTION));
    }
}
