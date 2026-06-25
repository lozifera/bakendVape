package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import com.example.bakend_vape.pedido.application.dto.CrearPedidoRequest;
import com.example.bakend_vape.pedido.domain.event.PedidoCreadoEvent;
import com.example.bakend_vape.pedido.application.dto.PedidoProductoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoProductoResponse;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.CrearPedidoUseCase;
import com.example.bakend_vape.pedido.domain.model.EstadoPedido;
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
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.context.ApplicationEventPublisher;
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
    private final DireccionRepository direccionRepository;
    private final MovimientoPuntosRepository movimientoPuntosRepository;
    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;
    private final ApplicationEventPublisher eventPublisher;

    public CrearPedidoService(PedidoRepository pedidoRepository,
                              PedidoProductoRepository pedidoProductoRepository,
                              ProductoRepository productoRepository,
                              UsuarioRepository usuarioRepository,
                              DireccionRepository direccionRepository,
                              MovimientoPuntosRepository movimientoPuntosRepository,
                              CarritoRepository carritoRepository,
                              CarritoProductoRepository carritoProductoRepository,
                              UsuarioAutenticadoService usuarioAutenticadoService,
                              AuditoriaService auditoriaService,
                              ApplicationEventPublisher eventPublisher) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.direccionRepository = direccionRepository;
        this.movimientoPuntosRepository = movimientoPuntosRepository;
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public PedidoResponse execute(CrearPedidoRequest request) {
        // 1. Validar usuario
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // 1b. Si se envía id_direccion, obtener datos desde la tabla direccion
        String direccionEnvio = request.getDireccionEnvio();
        String referenciaEnvio = request.getReferenciaEnvio();

        if (request.getIdDireccion() != null) {
            Direccion dir = direccionRepository.findById(request.getIdDireccion())
                    .orElseThrow(() -> new NotFoundException("Dirección no encontrada"));
            direccionEnvio = dir.getDireccion();
            referenciaEnvio = dir.getReferencia();
        }

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

        // 2b. Aplicar descuento por canje de puntos (1 punto = Bs. 1)
        Integer puntosUsados = request.getPuntosUsados();
        if (puntosUsados != null && puntosUsados > 0) {
            if (puntosUsados > usuario.getPuntos_actuales()) {
                throw new BusinessException("Puntos insuficientes. Disponibles: " + usuario.getPuntos_actuales());
            }
            BigDecimal descuento = BigDecimal.valueOf(puntosUsados).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN);
            total = total.subtract(descuento);
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                total = BigDecimal.ZERO;
            }
        }

        // 3. Registrar pedido
        Pedido pedido = new Pedido(null, usuario, new Money(total),
                LocalDateTime.now(), EstadoPedido.PENDIENTE,
                direccionEnvio, referenciaEnvio,
                LocalDateTime.now(), LocalDateTime.now());
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

        // 5. Generar puntos por compra y registrar canje de puntos
        int puntosGanados = total.divide(BigDecimal.TEN, 0, RoundingMode.DOWN).intValue();
        boolean hayPuntosGanados = puntosGanados > 0;
        boolean hayCanje = puntosUsados != null && puntosUsados > 0;

        if (hayPuntosGanados) {
            MovimientoPuntos movimiento = new MovimientoPuntos(
                    null, usuario, new Puntos(puntosGanados),
                    MotivoMovimiento.COMPRA, pedidoGuardado.getIdPedido(),
                    LocalDateTime.now(), LocalDateTime.now()
            );
            movimientoPuntosRepository.save(movimiento);
            usuario.setPuntos_actuales(usuario.getPuntos_actuales() + puntosGanados);
        }

        if (hayCanje) {
            MovimientoPuntos canje = new MovimientoPuntos(
                    null, usuario, new Puntos(puntosUsados),
                    MotivoMovimiento.CANJE, pedidoGuardado.getIdPedido(),
                    LocalDateTime.now(), LocalDateTime.now()
            );
            movimientoPuntosRepository.save(canje);
            usuario.setPuntos_actuales(usuario.getPuntos_actuales() - puntosUsados);
        }

        if (hayPuntosGanados || hayCanje) {
            usuarioRepository.save(usuario);
        }

        // 6. Limpiar carrito del usuario
        carritoRepository.findByUsuarioId(usuario.getIdUsuario()).ifPresent(carrito ->
                carritoProductoRepository.deleteByCarritoId(carrito.getIdCarrito())
        );

        Usuario usuarioAuditoria = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuarioAuditoria,
                    AccionAuditoria.CREATE,
                    "pedido",
                    pedidoGuardado.getIdPedido(),
                    null,
                    "Total: " + total + " | Productos: " + request.getProductos().size()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        eventPublisher.publishEvent(new PedidoCreadoEvent(usuario.getIdUsuario(), pedidoGuardado.getIdPedido()));

        return new PedidoResponse(
                pedidoGuardado.getIdPedido(),
                usuario.getIdUsuario(),
                total,
                pedidoGuardado.getFecha(),
                pedidoGuardado.getEstado(),
                pedidoGuardado.getDireccionEnvio(),
                pedidoGuardado.getReferenciaEnvio(),
                itemsResponse,
                pedidoGuardado.getCreatedAt()
        );
    }
}
