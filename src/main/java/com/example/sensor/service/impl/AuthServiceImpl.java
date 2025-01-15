package com.example.sensor.service.impl;

import com.example.sensor.dto.request.AuthRequestDto;
import com.example.sensor.dto.request.RegisterUserRequestDto;
import com.example.sensor.dto.response.AuthResponseDto;
import com.example.sensor.entities.Token;
import com.example.sensor.entities.User;
import com.example.sensor.entities.enums.Role;
import com.example.sensor.entities.enums.TokenType;
import com.example.sensor.exception.DbNotFoundException;
import com.example.sensor.repositories.TokenRepository;
import com.example.sensor.repositories.UserRepository;
import com.example.sensor.security.JwtService;
import com.example.sensor.security.UserPrincipal;
import com.example.sensor.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public void registerUser(RegisterUserRequestDto registerUserRequestDto) {
        userRepository.findUserByEmail(registerUserRequestDto.getEmail().trim())
                .ifPresent(usr -> {
                    throw new IllegalArgumentException("User with email "+ registerUserRequestDto.getEmail() + " already exists");
                });

        if(!registerUserRequestDto.getPassword().trim().equals(registerUserRequestDto.getConfirmPassword().trim())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setEmail(registerUserRequestDto.getEmail());
        user.setFirstName(registerUserRequestDto.getFirstName());
        user.setLastName(registerUserRequestDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setRole(Role.USER);

        log.info("Registered user: {}", user);
        userRepository.save(user);
    }

    @Override
    public AuthResponseDto authenticateUser(AuthRequestDto authRequest) {
        User user = userRepository.findUserByEmail(authRequest.getEmail())
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "User doesn't exist"));

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        UserDetails userDetails = new UserPrincipal(user);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException{

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token is empty");
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {

            var user = userRepository.findUserByEmail(userEmail)
                    .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Token is invalid"));

            UserDetails userDetails = new UserPrincipal(user);

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

            } else {
                throw new IllegalArgumentException("Token is invalid");
            }
        } else
            throw new IllegalArgumentException("Token is empty");
    }
}
