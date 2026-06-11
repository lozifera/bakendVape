package com.example.bakend_vape.usuario.infrastructure.controller;

import com.example.bakend_vape.usuario.application.dto.LoginRequest;
import com.example.bakend_vape.usuario.application.dto.LoginResponse;
import com.example.bakend_vape.usuario.application.usecase.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase login;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        return login.execute(request);
    }

}
