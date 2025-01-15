package com.example.sensor.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementRequestDto {

    @NotNull(message = "Value cannot be null")
    @Min(value = -100, message = "Temperature cannot be lower than -100")
    @Max(value = 100, message = "Temperature cannot be higher than 100")
    private Double value;

    @NotNull(message = "Raining cannot be null")
    private boolean raining;

    @JsonProperty("sensor")
    private Sensor sensor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Sensor{
        @JsonProperty("name")
        private String name;
    }
}
