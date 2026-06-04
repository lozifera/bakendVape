package com.example.bakend_vape.valoracion.domain.repository;

import com.example.bakend_vape.valoracion.domain.model.Valoracion;

import java.util.List;
import java.util.Optional;

public interface ValoracionRepository {

    Valoracion save(Valoracion valoracion);

    Optional<Valoracion> findById(Long id);

    List<Valoracion> findByProductoId(Long productoId);

    List<Valoracion> findByUsuarioId(Long usuarioId);

}
