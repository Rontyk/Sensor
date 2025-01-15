package com.example.sensor.controller;

import com.example.sensor.dto.request.MeasurementRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
@Slf4j
public class MeasurementController {

    @PostMapping("/add")
    public ResponseEntity<Void> addMeasurement(MeasurementRequestDto measurementRequestDto){

    }
}
