package com.example.sensor.service;


import com.example.sensor.dto.request.AuthRequestDto;
import com.example.sensor.dto.request.RegisterUserRequestDto;
import com.example.sensor.dto.response.AuthResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    void registerUser(RegisterUserRequestDto user);

    AuthResponseDto authenticateUser(AuthRequestDto auth);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
