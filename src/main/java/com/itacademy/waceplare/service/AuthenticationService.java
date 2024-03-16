package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AuthenticationRequestDto;
import com.itacademy.waceplare.dto.AuthenticationResponseDto;
import com.itacademy.waceplare.dto.RegisterRequestDto;
import com.itacademy.waceplare.exception.UserNotFoundException;
import com.itacademy.waceplare.model.Role;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.UserRepository;
import com.itacademy.waceplare.security.JwtService;
import com.itacademy.waceplare.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ValidationUtils validationUtils;

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        validationUtils.validationRequest(request);

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .number(request.getNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(""));

        var jwtToken = jwtService.generateToken((User) authentication.getPrincipal());
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}
