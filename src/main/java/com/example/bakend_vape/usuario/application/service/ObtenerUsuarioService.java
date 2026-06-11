package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;
import com.example.bakend_vape.usuario.application.usecase.ObtenerUsuarioUseCase;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerUsuarioService implements ObtenerUsuarioUseCase  {

    private final UsuarioRepository usuarioRepository;

    public ObtenerUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioResponse> execute() {

        return usuarioRepository.findAll().stream().map(
                usuario -> new UsuarioResponse(
                        usuario.getIdUsuario(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail().getValue(),
                        usuario.getEsVip(),
                        usuario.getPuntos_actuales(),
                        usuario.getRol().getNombre()
                )
        ).toList(
        );
    }
}
