package com.example.bakend_vape.carrito.application.service;

import com.example.bakend_vape.carrito.application.dto.CarritoProductoResponse;
import com.example.bakend_vape.carrito.application.dto.CarritoResponse;
import com.example.bakend_vape.carrito.application.usecase.ObtenerCarritoUseCase;
import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerCarritoService implements ObtenerCarritoUseCase {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImagenProductoRepository imagenProductoRepository;

    public ObtenerCarritoService(CarritoRepository carritoRepository,
                                 CarritoProductoRepository carritoProductoRepository,
                                 UsuarioRepository usuarioRepository,
                                 ImagenProductoRepository imagenProductoRepository) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
        this.usuarioRepository = usuarioRepository;
        this.imagenProductoRepository = imagenProductoRepository;
    }

    @Override
    public CarritoResponse execute(Long idUsuario) {
        var usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuarioId(idUsuario)
                .orElseGet(() -> carritoRepository.save(new Carrito(
                        null, usuario, null, true, LocalDateTime.now(), LocalDateTime.now()
                )));

        return buildResponse(carrito);
    }

    public CarritoResponse executeBySession(String sessionId) {
        Carrito carrito = carritoRepository.findBySessionId(sessionId)
                .orElseGet(() -> carritoRepository.save(new Carrito(
                        null, null, sessionId, true, LocalDateTime.now(), LocalDateTime.now()
                )));

        return buildResponse(carrito);
    }

    private CarritoResponse buildResponse(Carrito carrito) {
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
        return new CarritoResponse(carrito.getIdCarrito(), idUsuario, carrito.getSessionId(), itemsResponse, total);
    }
}