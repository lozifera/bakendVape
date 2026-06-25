package com.example.bakend_vape.carrito.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.carrito.application.dto.AgregarProductoCarritoRequest;
import com.example.bakend_vape.carrito.application.dto.CarritoProductoResponse;
import com.example.bakend_vape.carrito.application.dto.CarritoResponse;
import com.example.bakend_vape.carrito.application.usecase.AgregarProductoCarritoUseCase;
import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgregarProductoCarritoService implements AgregarProductoCarritoUseCase {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public AgregarProductoCarritoService(CarritoRepository carritoRepository,
                                         CarritoProductoRepository carritoProductoRepository,
                                         ProductoRepository productoRepository,
                                         UsuarioRepository usuarioRepository,
                                         ImagenProductoRepository imagenProductoRepository,
                                         UsuarioAutenticadoService usuarioAutenticadoService,
                                         AuditoriaService auditoriaService) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public CarritoResponse execute(Long idUsuario, AgregarProductoCarritoRequest request) {
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (producto.getStock() < request.getCantidad()) {
            throw new BusinessException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        Carrito carrito = resolverCarrito(idUsuario, request.getSessionId());

        Optional<CarritoProducto> existente = carritoProductoRepository
                .findByCarritoId(carrito.getIdCarrito())
                .stream()
                .filter(cp -> cp.getProducto().getIdProducto().equals(request.getIdProducto()))
                .findFirst();

        if (existente.isPresent()) {
            CarritoProducto cp = existente.get();
            int nuevaCantidad = cp.getCantidad() + request.getCantidad();
            if (producto.getStock() < nuevaCantidad) {
                throw new BusinessException("Stock insuficiente para esa cantidad total");
            }
            BigDecimal precio = producto.getPrecio();
            cp.setCantidad(nuevaCantidad);
            cp.setPrecioVenta(new Money(precio));
            cp.setSubtotal(new Money(precio.multiply(BigDecimal.valueOf(nuevaCantidad))));
            cp.setUpdatedAt(LocalDateTime.now());
            carritoProductoRepository.save(cp);
        } else {
            BigDecimal precio = producto.getPrecio();
            CarritoProducto nuevo = new CarritoProducto(
                    null, producto, carrito, request.getCantidad(),
                    new Money(precio),
                    new Money(precio.multiply(BigDecimal.valueOf(request.getCantidad()))),
                    LocalDateTime.now(), LocalDateTime.now()
            );
            carritoProductoRepository.save(nuevo);
        }

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.CREATE,
                    "carrito_producto",
                    carrito.getIdCarrito(),
                    null,
                    "Producto: " + producto.getNombre() + " | Cantidad: " + request.getCantidad()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buildResponse(carrito);
    }

    private Carrito resolverCarrito(Long idUsuario, String sessionId) {
        if (idUsuario != null) {
            return carritoRepository.findByUsuarioId(idUsuario)
                    .orElseGet(() -> {
                        var usuario = usuarioRepository.findById(idUsuario)
                                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
                        return carritoRepository.save(new Carrito(
                                null, usuario, null, true,
                                LocalDateTime.now(), LocalDateTime.now()
                        ));
                    });
        }

        if (sessionId == null || sessionId.isBlank()) {
            throw new BusinessException("Se requiere session_id para carritos de visitantes");
        }

        return carritoRepository.findBySessionId(sessionId)
                .orElseGet(() -> carritoRepository.save(new Carrito(
                        null, null, sessionId, true,
                        LocalDateTime.now(), LocalDateTime.now()
                )));
    }

    CarritoResponse buildResponse(Carrito carrito) {
        List<CarritoProducto> items = carritoProductoRepository.findByCarritoId(carrito.getIdCarrito());
        List<CarritoProductoResponse> itemsResponse = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CarritoProducto cp : items) {
            List<ImagenProducto> rels = imagenProductoRepository.findByProductoId(cp.getProducto().getIdProducto());
            List<ImagenResponse> imagenes = rels.stream()
                    .map(r -> new ImagenResponse(r.getImagen().getIdImagen(), r.getImagen().getUrl(),
                            r.getImagen().getNombre(), r.getImagen().getEstado()))
                    .toList();

            Producto p = cp.getProducto();
            ProductoResponse pr = new ProductoResponse(
                    p.getIdProducto(), p.getNombre(), p.getDescripcion(), p.getPrecio(),
                    p.getStock(), p.getStockMinimo(),
                    new com.example.bakend_vape.categoria.application.dto.CategoriaResponse(
                            p.getCategoria().getId_categoria(), p.getCategoria().getNombre(),
                            new ArrayList<>(), p.getCategoria().getCreated_at(), p.getCategoria().getUpdated_at()),
                    new com.example.bakend_vape.marca.application.dto.MarcaResponse(
                            p.getMarca().getId_marca(), p.getMarca().getNombre(),
                            p.getMarca().getCreated_at(), p.getMarca().getUpdated_at()),
                    imagenes,
                    new ArrayList<>(),  // ← atributos vacío
                    p.getCreatedAt(), p.getUpdatedAt()
            );

            itemsResponse.add(new CarritoProductoResponse(
                    cp.getIdCarritoProducto(), pr, cp.getCantidad(),
                    cp.getPrecioVenta().value(), cp.getSubtotal().value()
            ));
            total = total.add(cp.getSubtotal().value());
        }

        Long idUsuario = carrito.getUsuario() != null ? carrito.getUsuario().getIdUsuario() : null;
        String sessionId = carrito.getSessionId();

        return new CarritoResponse(carrito.getIdCarrito(), idUsuario, sessionId, itemsResponse, total);
    }
}