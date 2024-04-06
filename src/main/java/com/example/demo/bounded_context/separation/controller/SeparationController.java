package com.example.demo.bounded_context.separation.controller;

import com.example.demo.bounded_context.category.entity.MainCategory;
import com.example.demo.bounded_context.separation.dto.DetectResponse;
import com.example.demo.bounded_context.separation.dto.ImageRequest;
import com.example.demo.bounded_context.separation.dto.SolutionResponse;
import com.example.demo.bounded_context.separation.util.CategoryProvider;
import com.example.demo.bounded_context.separation.service.SeparationService;
import com.example.demo.bounded_context.separation.service.DetectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/separation")
@Tag(name = "Separation", description = "분리배출 방법 API")
public class SeparationController {
    private final SeparationService separationService;
    private final DetectService detectService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return detectService.test();
    }

    @GetMapping("")
    @Operation(summary = "이미지 검색", description = "사진을 이용한 인공지능 물질 분류 및 배출방법 조회")
    public ResponseEntity solution(@RequestBody ImageRequest request) {

        List<SolutionResponse> responses = new ArrayList<>();
        List<DetectResponse> detects = detectService.detect(request.getUrl());

        for(DetectResponse detect : detects){
            String name = CategoryProvider.categoryMapper.get(detect.getName());
            MainCategory mainCategory = separationService.read(name);
            responses.add(SolutionResponse.of(mainCategory));
        }

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{keyword}")
    @Operation(summary = "키워드 검색", description = "키워드를 사용한 분리배출 방법 조회")
    public ResponseEntity<SolutionResponse> solution(@PathVariable String keyword) {
        MainCategory mainCategory = separationService.read(keyword);
        return ResponseEntity.ok().body(SolutionResponse.of(mainCategory));
    }
}
