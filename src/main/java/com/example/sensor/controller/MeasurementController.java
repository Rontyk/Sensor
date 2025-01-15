package com.example.sensor.controller;

import com.example.sensor.dto.request.MeasurementRequestDto;
import com.example.sensor.dto.response.MeasurementResponseDto;
import com.example.sensor.service.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
@Slf4j
public class MeasurementController {
    private final MeasurementService measurementService;

    @GetMapping
    public ResponseEntity<List<MeasurementResponseDto>> getAll(){
        return ResponseEntity.ok(measurementService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addMeasurement(@Valid @RequestBody MeasurementRequestDto measurementRequestDto){
        measurementService.addMeasurement(measurementRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> rainyDaysCount(){
        return ResponseEntity.ok(measurementService.rainyDaysCount());
    }
}
