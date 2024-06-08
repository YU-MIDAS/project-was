package com.example.demo.bounded_context.image.controller;

import com.example.demo.bounded_context.image.service.S3ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class ImageController {

    private final S3ImageService s3ImageService;

    @PostMapping("")
    @Operation(summary = "이미지 클라우드 업로드", description = "모든 사용자는 서버 클라우드에 이미지를 업로드 할 수 있다.")
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image){
        String profileImage = s3ImageService.uploadImage(image);
        return ResponseEntity.ok(profileImage);
    }
}
