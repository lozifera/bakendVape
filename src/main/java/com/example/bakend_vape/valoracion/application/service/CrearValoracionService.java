package com.example.bakend_vape.valoracion.application.service;

import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.puntos.domain.model.MotivoMovimiento;
import com.example.bakend_vape.puntos.domain.model.MovimientoPuntos;
import com.example.bakend_vape.puntos.domain.repository.MovimientoPuntosRepository;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.exception.ValidationException;
import com.example.bakend_vape.shared.domain.valueObject.Puntos;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.valoracion.application.dto.CrearValoracionRequest;
import com.example.bakend_vape.valoracion.application.dto.ValoracionResponse;
import com.example.bakend_vape.valoracion.application.usecase.CrearValoracionUseCase;
import com.example.bakend_vape.valoracion.domain.model.Valoracion;
import com.example.bakend_vape.valoracion.domain.repository.ValoracionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CrearValoracionService implements CrearValoracionUseCase {

    private final ValoracionRepository valoracionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;
    private final MovimientoPuntosRepository movimientoPuntosRepository;

    public CrearValoracionService(ValoracionRepository valoracionRepository,
                                  UsuarioRepository usuarioRepository,
                                  ProductoRepository productoRepository,
                                  PedidoProductoRepository pedidoProductoRepository,
                                  MovimientoPuntosRepository movimientoPuntosRepository) {
        this.valoracionRepository = valoracionRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.movimientoPuntosRepository = movimientoPuntosRepository;
    }

    @Override
    public ValoracionResponse execute(CrearValoracionRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        // Regla: solo puede valorar si compró el producto
        boolean haComprado = pedidoProductoRepository.existsByPedidoIdAndProductoId(
                request.getIdUsuario(), request.getIdProducto());
        // Nota: la query correcta es por usuarioId → pedidos → productos
        // Verificamos que exista al menos un PedidoProducto con ese producto para pedidos del usuario
        boolean haCompradoReal = pedidoProductoRepository
                .findByProductoId(request.getIdProducto())
                .stream()
                .anyMatch(pp -> pp.getPedido().getUsuario().getIdUsuario().equals(request.getIdUsuario()));

        if (!haCompradoReal) {
            throw new BusinessException("Solo puedes valorar productos que hayas comprado");
        }

        // Regla: una valoración por producto por usuario
        boolean yaValoro = valoracionRepository.findByUsuarioId(request.getIdUsuario())
                .stream()
                .anyMatch(v -> v.getProducto().getIdProducto().equals(request.getIdProducto()));

        if (yaValoro) {
            throw new BusinessException("Ya has valorado este producto");
        }

        if (request.getPuntuacion() < 1 || request.getPuntuacion() > 5) {
            throw new ValidationException("La puntuación debe estar entre 1 y 5");
        }

        Valoracion valoracion = new Valoracion(
                null, usuario, producto,
                request.getPuntuacion(), request.getComentario(),
                LocalDateTime.now(), LocalDateTime.now()
        );

        Valoracion guardada = valoracionRepository.save(valoracion);

        // Otorgar 2 puntos por valorar
        MovimientoPuntos movimiento = new MovimientoPuntos(
                null, usuario, new Puntos(2),
                MotivoMovimiento.VALORACION, guardada.getIdValoracion(),
                LocalDateTime.now(), LocalDateTime.now()
        );
        movimientoPuntosRepository.save(movimiento);
        usuario.setPuntos_actuales(usuario.getPuntos_actuales() + 2);
        usuarioRepository.save(usuario);

        return new ValoracionResponse(
                guardada.getIdValoracion(),
                usuario.getIdUsuario(),
                usuario.getNombre() + " " + usuario.getApellido(),
                producto.getIdProducto(),
                guardada.getPuntuacion(),
                guardada.getComentario(),
                guardada.getCreatedAt()
        );
    }
}
