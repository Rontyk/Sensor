package com.example.sensor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "measurement")
public class Measurement extends AbstractEntity<Long>{

    @NotNull(message = "Value cannot be null")
    @Min(value = -100, message = "Temperature cannot be lower than -100")
    @Max(value = 100, message = "Temperature cannot be higher than 100")
    private Double value;

    @NotNull(message = "Raining cannot be null")
    private boolean raining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}
