package com.burakcanaksoy.atomictemperatureaggregator.controller;

import com.burakcanaksoy.atomictemperatureaggregator.dto.ApiResponse;
import com.burakcanaksoy.atomictemperatureaggregator.dto.TemperatureRequest;
import com.burakcanaksoy.atomictemperatureaggregator.dto.TemperatureResponse;
import com.burakcanaksoy.atomictemperatureaggregator.service.TemperatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/temperature")
public class TemperatureController { 

    private final TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService){
        this.temperatureService = temperatureService;
    }

    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<TemperatureResponse>> convertTemperature(@RequestBody TemperatureRequest request){
        TemperatureResponse response = temperatureService.convertTemperature(request);
        return ResponseEntity.ok(ApiResponse.success("Sıcaklık başarılı bir şekilde dönüştürüldü.",response));
    }

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<TemperatureResponse>> convertTemperature(@RequestBody TemperatureRequest request){
        TemperatureResponse response = temperatureService.convertTemperature(request);
        return ResponseEntity.ok(ApiResponse.success("Sıcaklık başarılı bir şekilde dönüştürüldü.",response));
    }

}
