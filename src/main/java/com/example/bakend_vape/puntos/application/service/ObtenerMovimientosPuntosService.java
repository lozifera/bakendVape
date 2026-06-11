package com.example.bakend_vape.puntos.application.service;

import com.example.bakend_vape.puntos.application.dto.MovimientoPuntosResponse;
import com.example.bakend_vape.puntos.application.usecase.ObtenerMovimientosPuntosUseCase;
import com.example.bakend_vape.puntos.domain.repository.MovimientoPuntosRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerMovimientosPuntosService implements ObtenerMovimientosPuntosUseCase {

    private final MovimientoPuntosRepository movimientoPuntosRepository;
    private final UsuarioRepository usuarioRepository;

    public ObtenerMovimientosPuntosService(MovimientoPuntosRepository movimientoPuntosRepository,
                                           UsuarioRepository usuarioRepository) {
        this.movimientoPuntosRepository = movimientoPuntosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<MovimientoPuntosResponse> execute(Long idUsuario) {
        usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return movimientoPuntosRepository.findByUsuarioId(idUsuario).stream().map(m ->
                new MovimientoPuntosResponse(
                        m.getIdMovimientoPuntos(),
                        m.getUsuario().getIdUsuario(),
                        m.getPuntos().value(),
                        m.getMotivo(),
                        m.getReferenciaId(),
                        m.getCreatedAt()
                )
        ).toList();
    }
}
