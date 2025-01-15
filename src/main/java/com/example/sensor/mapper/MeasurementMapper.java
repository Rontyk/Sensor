package com.example.sensor.mapper;

import com.example.sensor.dto.response.MeasurementResponseDto;
import com.example.sensor.entities.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {

    public MeasurementResponseDto toDto(Measurement measurement) {
        return MeasurementResponseDto.builder()
                .value(measurement.getValue())
                .raining(measurement.isRaining())
                .sensor(MeasurementResponseDto.Sensor.builder()
                        .name(measurement.getSensor().getName())
                        .build())
                .build();
    }
}
