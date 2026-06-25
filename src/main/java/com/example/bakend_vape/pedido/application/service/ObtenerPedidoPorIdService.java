package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.pedido.application.dto.PedidoProductoResponse;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.ObtenerPedidoPorIdUseCase;
import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerPedidoPorIdService implements ObtenerPedidoPorIdUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;

    public ObtenerPedidoPorIdService(PedidoRepository pedidoRepository,
                                     PedidoProductoRepository pedidoProductoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
    }

    @Override
    public PedidoResponse execute(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con id: " + idPedido));

        List<PedidoProducto> items = pedidoProductoRepository.findByPedidoId(idPedido);
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
    }
}
