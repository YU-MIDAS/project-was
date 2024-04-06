package com.example.demo.bounded_context.separation.service;

import com.example.demo.bounded_context.separation.dto.DetectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DetectService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ConcurrentHashMap<String, String> params = new ConcurrentHashMap<>(1);
    @Value("${server.detect-api}")
    private String pythonServerUrl;

    public ResponseEntity test() {
        ResponseEntity<String> response = restTemplate.getForEntity(pythonServerUrl, String.class);
        return response;
    }

    public List<DetectResponse> detect(String imageUrl)  {
        params.put("image_url", imageUrl);
        List<DetectResponse> detectResponseList = call(pythonServerUrl + "/models/detect/{image_url}", params);
        return detectResponseList;
    }

    public List<DetectResponse> call(String url, Map<String, String> params) {
        return restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<DetectResponse>>(){},
                        params)
                .getBody();
    }
}
