package com.example.sensor.controller;

import com.example.sensor.dto.request.MeasurementRequestDto;
import com.example.sensor.dto.request.PageRequestDto;
import com.example.sensor.dto.response.MeasurementResponseDto;
import com.example.sensor.entities.Measurement;
import com.example.sensor.service.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
@Slf4j
public class MeasurementController {
    private final MeasurementService measurementService;

    @GetMapping
    public ResponseEntity<Page<MeasurementResponseDto>> getAll(PageRequestDto dto) {
        Pageable pageable = dto.getPageable(dto);
        Page<MeasurementResponseDto> measurements = measurementService.findAll(pageable);
        return ResponseEntity.ok(measurements);
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
