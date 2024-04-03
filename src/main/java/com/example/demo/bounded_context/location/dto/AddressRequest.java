package com.example.demo.bounded_context.location.dto;

import lombok.Getter;

@Getter
public class AddressRequest {
    String state;
    String city;
}
