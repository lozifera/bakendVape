package com.example.bakend_vape.valoracion.application.service;

import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.valoracion.application.dto.ValoracionResponse;
import com.example.bakend_vape.valoracion.application.usecase.ObtenerValoracionesPorProductoUseCase;
import com.example.bakend_vape.valoracion.domain.repository.ValoracionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerValoracionesPorProductoService implements ObtenerValoracionesPorProductoUseCase {

    private final ValoracionRepository valoracionRepository;
    private final ProductoRepository productoRepository;

    public ObtenerValoracionesPorProductoService(ValoracionRepository valoracionRepository,
                                                  ProductoRepository productoRepository) {
        this.valoracionRepository = valoracionRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ValoracionResponse> execute(Long idProducto) {
        productoRepository.findById(idProducto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        return valoracionRepository.findByProductoId(idProducto).stream().map(v ->
                new ValoracionResponse(
                        v.getIdValoracion(),
                        v.getUsuario().getIdUsuario(),
                        v.getUsuario().getNombre() + " " + v.getUsuario().getApellido(),
                        v.getProducto().getIdProducto(),
                        v.getPuntuacion(),
                        v.getComentario(),
                        v.getCreatedAt()
                )
        ).toList();
    }
}
