package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.pedido.application.dto.ActualizarEstadoPedidoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoProductoResponse;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.ActualizarEstadoPedidoUseCase;
import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActualizarEstadoPedidoService implements ActualizarEstadoPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final PedidoProductoRepository pedidoProductoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public ActualizarEstadoPedidoService(PedidoRepository pedidoRepository,
                                         PedidoProductoRepository pedidoProductoRepository,
                                         UsuarioAutenticadoService usuarioAutenticadoService,
                                         AuditoriaService auditoriaService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoProductoRepository = pedidoProductoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public PedidoResponse execute(Long idPedido, ActualizarEstadoPedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con id: " + idPedido));

        String valorAnterior = pedido.getEstado().name();

        pedido.setEstado(request.getEstado());
        Pedido actualizado = pedidoRepository.save(pedido);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.UPDATE,
                    "pedido",
                    idPedido,
                    valorAnterior,
                    request.getEstado().name()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                actualizado.getIdPedido(),
                actualizado.getUsuario().getIdUsuario(),
                actualizado.getTotal().value(),
                actualizado.getFecha(),
                actualizado.getEstado(),
                actualizado.getDireccionEnvio(),
                actualizado.getReferenciaEnvio(),
                itemsResponse,
                actualizado.getCreatedAt()
        );
    }
}
