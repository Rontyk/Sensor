package com.example.sensor.controller;

import com.example.sensor.dto.request.SensorRequestDto;
import com.example.sensor.service.impl.SensorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sensors")
@Slf4j
public class SensorController {
    private final SensorServiceImpl sensorService;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody SensorRequestDto sensorRequestDto){
        sensorService.addingSensorByName(sensorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
