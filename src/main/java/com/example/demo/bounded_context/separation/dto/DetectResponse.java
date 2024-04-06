package com.example.demo.bounded_context.separation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DetectResponse {
    String name;
    Double confidence;
    List<Double> box;
}
