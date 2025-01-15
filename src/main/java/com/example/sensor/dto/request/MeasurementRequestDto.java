package com.example.sensor.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementRequestDto {
    private Double value;
    private boolean raining;
    private Sensor sensor;

    @Data
    @AllArgsConstructor
    public static class Sensor{
        private String name;
    }
}
