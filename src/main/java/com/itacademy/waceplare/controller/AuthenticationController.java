package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.service.AuthenticationService;
import com.itacademy.waceplare.dto.RegisterRequestDto;
import com.itacademy.waceplare.dto.AuthenticationRequestDto;
import com.itacademy.waceplare.dto.AuthenticationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/check")
    public Boolean authenticate() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

}
