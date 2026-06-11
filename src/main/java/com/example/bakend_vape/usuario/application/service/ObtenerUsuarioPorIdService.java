package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;
import com.example.bakend_vape.usuario.application.usecase.ObtenerUsuarioPorIdUseCase;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuarioPorIdService implements ObtenerUsuarioPorIdUseCase {

    private final UsuarioRepository usuarioRepository;

    public ObtenerUsuarioPorIdService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse execute(Long id) {

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));



        return new UsuarioResponse(
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
