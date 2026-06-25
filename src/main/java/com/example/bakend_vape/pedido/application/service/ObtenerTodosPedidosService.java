package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.pedido.application.dto.PedidoProductoResponse;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.ObtenerTodosPedidosUseCase;
import com.example.bakend_vape.pedido.domain.model.EstadoPedido;
import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTodosPedidosService implements ObtenerTodosPedidosUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;

    public ObtenerTodosPedidosService(PedidoRepository pedidoRepository,
                                      PedidoProductoRepository pedidoProductoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
    }

    @Override
    public List<PedidoResponse> execute(String estado) {
        List<Pedido> pedidos;

        if (estado != null && !estado.isBlank()) {
            EstadoPedido estadoEnum = EstadoPedido.valueOf(estado.toUpperCase());
            pedidos = pedidoRepository.findByEstado(estadoEnum);
        } else {
            pedidos = pedidoRepository.findAll();
        }

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
