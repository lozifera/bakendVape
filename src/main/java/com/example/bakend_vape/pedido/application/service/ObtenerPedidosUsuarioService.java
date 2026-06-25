package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.pedido.application.dto.PedidoProductoResponse;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.ObtenerPedidosUsuarioUseCase;
import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerPedidosUsuarioService implements ObtenerPedidosUsuarioUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;
    private final UsuarioRepository usuarioRepository;

    public ObtenerPedidosUsuarioService(PedidoRepository pedidoRepository,
                                        PedidoProductoRepository pedidoProductoRepository,
                                        UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<PedidoResponse> execute(Long idUsuario) {
        usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(idUsuario);

        return pedidos.stream().map(pedido -> {
            List<PedidoProducto> items = pedidoProductoRepository.findByPedidoId(pedido.getIdPedido());
            List<PedidoProductoResponse> itemsResponse = items.stream().map(pp ->
                    new PedidoProductoResponse(
                            pp.getIdPedidoProducto(),
                            pp.getProducto().getIdProducto(),
                            pp.getProducto().getNombre(),
                            pp.getCantidad(),
                            pp.getPrecioUnitario().value(),
                            pp.getSubtotal().value()
                    )
            ).toList();

            return new PedidoResponse(
                    pedido.getIdPedido(),
                    pedido.getUsuario().getIdUsuario(),
                    pedido.getTotal().value(),
                    pedido.getFecha(),
                    pedido.getEstado(),
                    pedido.getDireccionEnvio(),
                    pedido.getReferenciaEnvio(),
                    itemsResponse,
                    pedido.getCreatedAt()
            );
        }).toList();
    }
}
