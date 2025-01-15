package com.example.sensor.service;

import com.example.sensor.dto.request.SensorRequestDto;
import com.example.sensor.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorService {
    void addingSensorByName(SensorRequestDto sensorRequestDto);


}
