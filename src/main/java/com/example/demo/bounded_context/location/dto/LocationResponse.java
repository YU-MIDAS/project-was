package com.example.demo.bounded_context.location.dto;

import lombok.*;


@Getter
public class LocationResponse {
    String address;
    Double latitude;
    Double longitude;

    @Builder
    public LocationResponse(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
