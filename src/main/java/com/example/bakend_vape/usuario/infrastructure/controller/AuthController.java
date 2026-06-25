package com.example.bakend_vape.usuario.infrastructure.controller;

import com.example.bakend_vape.usuario.application.dto.LoginRequest;
import com.example.bakend_vape.usuario.application.dto.LoginResponse;
import com.example.bakend_vape.usuario.application.usecase.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase login;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = login.execute(request);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
