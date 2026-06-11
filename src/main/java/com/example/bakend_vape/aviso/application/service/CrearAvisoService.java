package com.example.bakend_vape.aviso.application.service;

import com.example.bakend_vape.aviso.application.dto.AvisoResponse;
import com.example.bakend_vape.aviso.application.dto.CrearAvisoRequest;
import com.example.bakend_vape.aviso.application.usecase.CrearAvisoUseCase;
import com.example.bakend_vape.aviso.domain.model.Aviso;
import com.example.bakend_vape.aviso.domain.repository.AvisoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CrearAvisoService implements CrearAvisoUseCase {

    private final AvisoRepository avisoRepository;
    private final UsuarioRepository usuarioRepository;

    public CrearAvisoService(AvisoRepository avisoRepository, UsuarioRepository usuarioRepository) {
        this.avisoRepository = avisoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public AvisoResponse execute(CrearAvisoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Aviso aviso = new Aviso(
                null,
                request.getTitulo(),
                request.getDescripcion(),
                request.getSoloVip() != null ? request.getSoloVip() : false,
                request.getFechaInicio(),
                request.getFechaFin(),
                usuario,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Aviso guardado = avisoRepository.save(aviso);

        return toResponse(guardado);
    }

    static AvisoResponse toResponse(Aviso a) {
        return new AvisoResponse(
                a.getIdAviso(),
                a.getTitulo(),
                a.getDescripcion(),
                a.getSoloVip(),
                a.getFechaInicio(),
                a.getFechaFin(),
                a.getUsuario().getIdUsuario(),
                a.getUsuario().getNombre() + " " + a.getUsuario().getApellido(),
                a.getCreatedAt()
        );
    }
}
