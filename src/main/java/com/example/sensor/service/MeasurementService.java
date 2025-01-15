package com.example.sensor.service;

import com.example.sensor.dto.request.MeasurementRequestDto;
import com.example.sensor.dto.response.MeasurementResponseDto;
import com.example.sensor.entities.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeasurementService {

    void addMeasurement(MeasurementRequestDto measurementRequestDto);

    Page<MeasurementResponseDto> findAll(Pageable pageable);

    Integer rainyDaysCount();
}
