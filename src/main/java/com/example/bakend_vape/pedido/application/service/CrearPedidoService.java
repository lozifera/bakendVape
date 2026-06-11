package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.pedido.application.dto.CrearPedidoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoProductoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoProductoResponse;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.CrearPedidoUseCase;
import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.puntos.domain.model.MotivoMovimiento;
import com.example.bakend_vape.puntos.domain.model.MovimientoPuntos;
import com.example.bakend_vape.puntos.domain.repository.MovimientoPuntosRepository;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.shared.domain.valueObject.Puntos;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrearPedidoService implements CrearPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimientoPuntosRepository movimientoPuntosRepository;
    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;

    public CrearPedidoService(PedidoRepository pedidoRepository,
                              PedidoProductoRepository pedidoProductoRepository,
                              ProductoRepository productoRepository,
                              UsuarioRepository usuarioRepository,
                              MovimientoPuntosRepository movimientoPuntosRepository,
                              CarritoRepository carritoRepository,
                              CarritoProductoRepository carritoProductoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimientoPuntosRepository = movimientoPuntosRepository;
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
    }

    @Override
    @Transactional
    public PedidoResponse execute(CrearPedidoRequest request) {
        // 1. Validar usuario
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (request.getProductos() == null || request.getProductos().isEmpty()) {
            throw new BusinessException("El pedido debe contener al menos un producto");
        }

        // 2. Validar stock y calcular total
        BigDecimal total = BigDecimal.ZERO;
        List<Producto> productosValidados = new ArrayList<>();

        for (PedidoProductoRequest item : request.getProductos()) {
            Producto producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + item.getIdProducto()));

            if (producto.getStock() < item.getCantidad()) {
                throw new BusinessException("Stock insuficiente para: " + producto.getNombre()
                        + ". Disponible: " + producto.getStock());
            }
            total = total.add(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
            productosValidados.add(producto);
        }

        // 3. Registrar pedido
        Pedido pedido = new Pedido(null, usuario, new Money(total),
                LocalDateTime.now(), true, LocalDateTime.now(), LocalDateTime.now());
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 4. Registrar productos del pedido y descontar stock
        List<PedidoProductoResponse> itemsResponse = new ArrayList<>();

        for (int i = 0; i < request.getProductos().size(); i++) {
            PedidoProductoRequest item = request.getProductos().get(i);
            Producto producto = productosValidados.get(i);

            BigDecimal precioUnitario = producto.getPrecio();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));

            PedidoProducto pp = new PedidoProducto(
                    null, pedidoGuardado, producto, item.getCantidad(),
                    new Money(precioUnitario), new Money(subtotal),
                    LocalDateTime.now(), LocalDateTime.now()
            );
            PedidoProducto ppGuardado = pedidoProductoRepository.save(pp);

            // Descontar stock
            producto.setStock(producto.getStock() - item.getCantidad());
            producto.setUpdatedAt(LocalDateTime.now());
            productoRepository.save(producto);

            itemsResponse.add(new PedidoProductoResponse(
                    ppGuardado.getIdPedidoProducto(),
                    producto.getIdProducto(),
                    producto.getNombre(),
                    item.getCantidad(),
                    precioUnitario,
                    subtotal
            ));
        }

        // 5. Generar puntos (1 punto por cada 10 unidades monetarias del total)
        int puntosGanados = total.divide(BigDecimal.TEN, 0, RoundingMode.DOWN).intValue();
        if (puntosGanados > 0) {
            MovimientoPuntos movimiento = new MovimientoPuntos(
                    null, usuario, new Puntos(puntosGanados),
                    MotivoMovimiento.COMPRA, pedidoGuardado.getIdPedido(),
                    LocalDateTime.now(), LocalDateTime.now()
            );
            movimientoPuntosRepository.save(movimiento);

            // Actualizar puntos del usuario
            usuario.setPuntos_actuales(usuario.getPuntos_actuales() + puntosGanados);
            usuarioRepository.save(usuario);
        }

        // 6. Limpiar carrito del usuario
        carritoRepository.findByUsuarioId(usuario.getIdUsuario()).ifPresent(carrito ->
                carritoProductoRepository.deleteByCarritoId(carrito.getIdCarrito())
        );

        return new PedidoResponse(
                pedidoGuardado.getIdPedido(),
                usuario.getIdUsuario(),
                total,
                pedidoGuardado.getFecha(),
                pedidoGuardado.getEstado(),
                itemsResponse,
                pedidoGuardado.getCreatedAt()
        );
    }
}
