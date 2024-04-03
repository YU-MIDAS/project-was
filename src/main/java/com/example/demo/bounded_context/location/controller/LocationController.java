package com.example.demo.bounded_context.location.controller;

import com.example.demo.base.Resolver.AuthorizationHeader;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.service.AccountService;
import com.example.demo.bounded_context.location.dto.AddressRequest;
import com.example.demo.bounded_context.location.dto.LocationResponse;
import com.example.demo.bounded_context.location.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
@Tag(name = "Location", description = "수거함 위치 관련 API")
public class LocationController {
     final LocationService locationService;
     final AccountService accountService;

    @GetMapping("/medicine")
    @Operation(summary = "폐의약품 위치", description = "주소를 사용한 폐의약품 수거함 위치 조회")
    public ResponseEntity<List<LocationResponse>> findMedicine(@RequestBody AddressRequest addressRequest) {
        List<LocationResponse> response = locationService.findMedicine(addressRequest.getState(), addressRequest.getCity());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/medicine/{radius}")
    @Operation(summary = "폐의약품 위치", description = "사용자의 위치, 반경(m) 사용한 폐의약품 수거함 위치 조회")
    public ResponseEntity<List<LocationResponse>> findMedicine(@AuthorizationHeader Long id, @PathVariable Double radius) {
        Account account = accountService.read(id);
        if(account.getLatitude() == null || account.getLongitude() == null){
            throw new CustomException(ExceptionCode.EMPTY_LOCATION);
        }
        List<LocationResponse> response = locationService.findMedicine(account.getLatitude(), account.getLongitude(), radius);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/lampAndBattery")
    @Operation(summary = "폐형광등 및 폐건전지 위치", description = "주소를 사용한 폐형광등 및 폐건전지 수거함 위치 조회")
    public ResponseEntity<List<LocationResponse>> findLampAndBattery(@RequestBody AddressRequest addressRequest) {
        List<LocationResponse> response = locationService.findLampAndBattery(addressRequest.getState(), addressRequest.getCity());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/lampAndBattery/{radius}")
    @Operation(summary = "폐형광등 및 폐건전지 위치", description = "사용자의 위치, 반경(m) 사용한 폐형광등 및 폐건전지 수거함 위치 조회")
    public ResponseEntity<List<LocationResponse>> findLampAndBattery(@AuthorizationHeader Long id, @PathVariable Double radius) {
        Account account = accountService.read(id);
        if(account.getLatitude() == null || account.getLongitude() == null){
            throw new CustomException(ExceptionCode.EMPTY_LOCATION);
        }
        List<LocationResponse> response = locationService.findLampAndBattery(account.getLatitude(), account.getLongitude(), radius);
        return ResponseEntity.ok().body(response);
    }
}

