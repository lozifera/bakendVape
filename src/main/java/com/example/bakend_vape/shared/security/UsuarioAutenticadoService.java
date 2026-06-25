package com.example.bakend_vape.shared.security;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioAutenticadoService {

    private final UsuarioRepository usuarioRepository;

    public  Usuario obtenerUsuario() {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = auth.getName();

        return usuarioRepository
                .findByEmail(new Email(email))
                .orElseThrow();
    }
}