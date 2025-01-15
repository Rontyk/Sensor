package com.example.sensor.service.impl;

import com.example.sensor.dto.request.SensorRequestDto;
import com.example.sensor.entities.Sensor;
import com.example.sensor.exception.DbNotFoundException;
import com.example.sensor.repositories.SensorRepository;
import com.example.sensor.repositories.UserRepository;
import com.example.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SensorServiceImpl implements SensorService{

    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    @Override
    public void addingSensorByName(SensorRequestDto sensorRequestDto) {
        log.info(sensorRequestDto.getName());
        Optional<Sensor> sensor = sensorRepository.findByName(sensorRequestDto.getName());
        if(sensor.isPresent())
            throw new DbNotFoundException(HttpStatus.CONFLICT.getReasonPhrase(), "Sensor with this name is already created");
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Sensor saveSensor = new Sensor();
        saveSensor.setName(sensorRequestDto.getName());
        saveSensor.setUser(userRepository.findUserByEmail(currentUsername).get());
        sensorRepository.save(saveSensor);
    }
}