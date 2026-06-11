package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.usuario.application.dto.LoginRequest;
import com.example.bakend_vape.usuario.application.dto.LoginResponse;
import com.example.bakend_vape.usuario.application.usecase.LoginUseCase;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import com.example.bakend_vape.usuario.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse execute(LoginRequest request) {

        Usuario usuario =
                usuarioRepository
                        .findByEmail(
                                new Email(
                                        request.getEmail()
                                )
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Credenciales inválidas"
                                )
                        );

        boolean coincide =
                passwordEncoder.matches(
                        request.getPassword(),
                        usuario.getPassword().getValue()
                );

        if (!coincide) {
            throw new RuntimeException(
                    "Credenciales inválidas"
            );
        }

        String token =
                jwtService.generateToken(
                        usuario.getEmail().getValue()
                );

        return new LoginResponse(
                token,
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail().getValue(),
                usuario.getEsVip(),
                usuario.getPuntos_actuales(),
                usuario.getRol().getNombre()
        );
    }
}
