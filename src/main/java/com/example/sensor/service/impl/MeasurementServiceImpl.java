package com.example.sensor.service.impl;

import com.example.sensor.dto.request.MeasurementRequestDto;
import com.example.sensor.dto.response.MeasurementResponseDto;
import com.example.sensor.entities.Measurement;
import com.example.sensor.entities.Sensor;
import com.example.sensor.exception.DbNotFoundException;
import com.example.sensor.mapper.MeasurementMapper;
import com.example.sensor.repositories.MeasurementRepository;
import com.example.sensor.repositories.SensorRepository;
import com.example.sensor.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeasurementServiceImpl implements MeasurementService {
    private final SensorRepository sensorRepository;
    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    @Override
    public void addMeasurement(MeasurementRequestDto measurementRequestDto) {
        log.info(measurementRequestDto.toString());
        Sensor sensor = sensorRepository.findByName(measurementRequestDto.getSensor().getName())
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "Sensor with this name do not found"));
        Measurement measurement = new Measurement();
        measurement.setRaining(measurementRequestDto.isRaining());
        measurement.setValue(measurementRequestDto.getValue());
        measurement.setSensor(sensor);
        measurementRepository.save(measurement);
    }

    @Override
    public Page<MeasurementResponseDto> findAll(Pageable pageable) {
        return measurementRepository.findAll(pageable)
                .map(measurementMapper::toDto);
    }


    @Override
    public Integer rainyDaysCount() {
        return measurementRepository.countByRainingIsTrue();
    }
}
