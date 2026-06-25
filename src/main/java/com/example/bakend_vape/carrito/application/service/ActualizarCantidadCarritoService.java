package com.example.bakend_vape.carrito.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.carrito.application.dto.ActualizarCantidadRequest;
import com.example.bakend_vape.carrito.application.dto.CarritoResponse;
import com.example.bakend_vape.carrito.application.usecase.ActualizarCantidadCarritoUseCase;
import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ActualizarCantidadCarritoService implements ActualizarCantidadCarritoUseCase {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;
    private final AgregarProductoCarritoService helper;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public ActualizarCantidadCarritoService(CarritoRepository carritoRepository,
                                            CarritoProductoRepository carritoProductoRepository,
                                            AgregarProductoCarritoService helper,
                                            UsuarioAutenticadoService usuarioAutenticadoService,
                                            AuditoriaService auditoriaService) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
        this.helper = helper;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public CarritoResponse execute(Long idUsuario, Long idCarritoProducto,
                                   ActualizarCantidadRequest request, String sessionId) {
        Carrito carrito = resolverCarrito(idUsuario, sessionId);

        CarritoProducto cp = carritoProductoRepository.findById(idCarritoProducto)
                .orElseThrow(() -> new NotFoundException("Item de carrito no encontrado"));

        if (!cp.getCarrito().getIdCarrito().equals(carrito.getIdCarrito())) {
            throw new BusinessException("El item no pertenece a este carrito");
        }

        int stock = cp.getProducto().getStock();
        if (request.getCantidad() > stock) {
            throw new BusinessException("Stock insuficiente. Disponible: " + stock);
        }
        if (request.getCantidad() <= 0) {
            throw new BusinessException("La cantidad debe ser mayor a cero");
        }

        BigDecimal precio = cp.getProducto().getPrecio();
        cp.setCantidad(request.getCantidad());
        cp.setPrecioVenta(new Money(precio));
        cp.setSubtotal(new Money(precio.multiply(BigDecimal.valueOf(request.getCantidad()))));
        cp.setUpdatedAt(LocalDateTime.now());
        carritoProductoRepository.save(cp);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.UPDATE,
                    "carrito_producto",
                    idCarritoProducto,
                    "cantidad anterior",
                    "Nueva cantidad: " + request.getCantidad()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return helper.buildResponse(carrito);
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
