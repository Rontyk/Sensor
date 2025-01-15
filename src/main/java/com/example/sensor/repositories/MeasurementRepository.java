package com.example.sensor.repositories;

import com.example.sensor.entities.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Integer countByRainingIsTrue();
}
