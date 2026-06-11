package com.example.bakend_vape.usuario.application.usecase;

import com.example.bakend_vape.usuario.application.dto.LoginRequest;
import com.example.bakend_vape.usuario.application.dto.LoginResponse;

public interface LoginUseCase {

    LoginResponse execute(LoginRequest request);

}
