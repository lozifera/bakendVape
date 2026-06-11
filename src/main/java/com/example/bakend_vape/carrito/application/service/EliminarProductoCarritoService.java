package com.example.bakend_vape.carrito.application.service;

import com.example.bakend_vape.carrito.application.usecase.EliminarProductoCarritoUseCase;
import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EliminarProductoCarritoService implements EliminarProductoCarritoUseCase {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;

    public EliminarProductoCarritoService(CarritoRepository carritoRepository,
                                          CarritoProductoRepository carritoProductoRepository) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
    }

    @Override
    public void execute(Long idUsuario, Long idCarritoProducto, String sessionId) {
        Carrito carrito = resolverCarrito(idUsuario, sessionId);

        CarritoProducto cp = carritoProductoRepository.findById(idCarritoProducto)
                .orElseThrow(() -> new NotFoundException("Item de carrito no encontrado"));

        if (!cp.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())) {
            throw new BusinessException("El item no pertenece a este carrito");
        }

        carritoProductoRepository.deleteById(idCarritoProducto);
    }

    private Carrito resolverCarrito(Long idUsuario, String sessionId) {
        if (idUsuario != null) {
            return carritoRepository.findByUsuarioId(idUsuario)
                    .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));
        }
        if (sessionId != null && !sessionId.isBlank()) {
            return carritoRepository.findBySessionId(sessionId)
                    .orElseThrow(() -> new NotFoundException("Carrito no encontrado para la sesión"));
        }
        throw new BusinessException("Se requiere idUsuario o sessionId");
    }
}
