package com.example.bakend_vape.valoracion.infrastructure.persistence.jpa;

import com.example.bakend_vape.valoracion.infrastructure.persistence.entity.ValoracionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaValoracionRepository extends JpaRepository<ValoracionEntity, Long> {

    List<ValoracionEntity> findByProductoIdProducto(Long idProducto);

    List<ValoracionEntity> findByUsuarioIdUsuario(Long idUsuario);

}
