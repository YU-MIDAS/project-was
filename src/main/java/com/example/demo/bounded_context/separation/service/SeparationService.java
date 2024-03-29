package com.example.demo.bounded_context.separation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SeparationService {
    final String pythonServerUrl = "http://localhost:5000";
    RestTemplate restTemplate;

    public ResponseEntity test() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(pythonServerUrl, String.class);
        return response;
    }

//    public ResponseEntity getCategory(String image) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Python 서버에 GET 요청을 보내고 응답을 받음
//        ResponseEntity<String> response = restTemplate.getForEntity(pythonServerUrl + "/detect/" + image, String.class);
//
//        // 응답 반환
//        return response;
//    }

//    private List<Long> getIds(String url, String image) {
//        return restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                image,
//                new ParameterizedTypeReference<List<Long>>() {
//                }
//        ).getBody();
//    }
}
