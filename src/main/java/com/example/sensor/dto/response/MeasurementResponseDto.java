package com.example.sensor.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementResponseDto {
    private Double value;
    private boolean raining;
    private Sensor sensor;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Sensor{
        private String name;
    }
}
