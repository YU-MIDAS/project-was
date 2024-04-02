package com.example.demo.bounded_context.separation.controller;

import com.example.demo.bounded_context.separation.service.SeparationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/separation")
@Tag(name = "Separation", description = "분리배출 방법 API")
public class SeparationController {
    private final SeparationService separationService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        separationService.test();
        return null;
    }

//    @GetMapping("/detect/{image}")
//    public ResponseEntity<String> detectImage(@PathVariable String image) {
//        separationService.getCategory(image);
//        return null;
//    }
}
