package com.example.bakend_vape.carrito.application.service;

import com.example.bakend_vape.carrito.application.dto.CarritoResponse;
import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Fusiona el carrito anónimo (identificado por sessionId) con el carrito
 * del usuario autenticado, sumando cantidades cuando el producto coincide
 * y respetando el stock disponible.
 * Al finalizar, elimina el carrito anónimo.
 */
@Service
public class FusionarCarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AgregarProductoCarritoService helper;

    public FusionarCarritoService(CarritoRepository carritoRepository,
                                  CarritoProductoRepository carritoProductoRepository,
                                  UsuarioRepository usuarioRepository,
                                  AgregarProductoCarritoService helper) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
        this.usuarioRepository = usuarioRepository;
        this.helper = helper;
    }

    @Transactional
    public CarritoResponse execute(Long idUsuario, String sessionId) {
        var usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // Obtener o crear carrito del usuario
        Carrito carritoUsuario = carritoRepository.findByUsuarioId(idUsuario)
                .orElseGet(() -> carritoRepository.save(new Carrito(
                        null, usuario, null, true, LocalDateTime.now(), LocalDateTime.now()
                )));

        // Carrito anónimo (puede no existir si el visitante no agregó nada)
        Optional<Carrito> optAnonimo = carritoRepository.findBySessionId(sessionId);

        if (optAnonimo.isPresent()) {
            Carrito carritoAnonimo = optAnonimo.get();
            List<CarritoProducto> itemsAnonimos = carritoProductoRepository
                    .findByCarritoId(carritoAnonimo.getIdCarrito());

            for (CarritoProducto itemAnonimo : itemsAnonimos) {
                Optional<CarritoProducto> existente = carritoProductoRepository
                        .findByCarritoId(carritoUsuario.getIdCarrito())
                        .stream()
                        .filter(cp -> cp.getProducto().getIdProducto()
                                .equals(itemAnonimo.getProducto().getIdProducto()))
                        .findFirst();

                if (existente.isPresent()) {
                    // Sumar cantidad, respetando stock
                    CarritoProducto cp = existente.get();
                    int stockDisponible = cp.getProducto().getStock();
                    int nuevaCantidad = Math.min(cp.getCantidad() + itemAnonimo.getCantidad(), stockDisponible);
                    BigDecimal precio = cp.getProducto().getPrecio();
                    cp.setCantidad(nuevaCantidad);
                    cp.setPrecioVenta(new Money(precio));
                    cp.setSubtotal(new Money(precio.multiply(BigDecimal.valueOf(nuevaCantidad))));
                    cp.setUpdatedAt(LocalDateTime.now());
                    carritoProductoRepository.save(cp);
                } else {
                    // Mover item al carrito del usuario
                    CarritoProducto nuevo = new CarritoProducto(
                            null,
                            itemAnonimo.getProducto(),
                            carritoUsuario,
                            itemAnonimo.getCantidad(),
                            itemAnonimo.getPrecioVenta(),
                            itemAnonimo.getSubtotal(),
                            LocalDateTime.now(), LocalDateTime.now()
                    );
                    carritoProductoRepository.save(nuevo);
                }
            }

            // Eliminar carrito anónimo
            carritoProductoRepository.deleteByCarritoId(carritoAnonimo.getIdCarrito());
            carritoRepository.deleteById(carritoAnonimo.getIdCarrito());
        }

        return helper.buildResponse(carritoUsuario);
    }
}
