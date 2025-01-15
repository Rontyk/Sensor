package com.example.sensor.service;

import com.example.sensor.dto.request.MeasurementRequestDto;
import com.example.sensor.dto.response.MeasurementResponseDto;
import com.example.sensor.entities.Measurement;

import java.util.List;

public interface MeasurementService {

    void addMeasurement(MeasurementRequestDto measurementRequestDto);

    List<MeasurementResponseDto> findAll();

    Integer rainyDaysCount();
}
