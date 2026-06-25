package com.example.bakend_vape.pedido.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.pedido.domain.event.PedidoCreadoEvent;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VipPedidoListener {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditoriaService auditoriaService;

    @Value("${vip.pedidos.minimos:10}")
    private int pedidosMinimos;

    public VipPedidoListener(PedidoRepository pedidoRepository,
                             UsuarioRepository usuarioRepository,
                             AuditoriaService auditoriaService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.auditoriaService = auditoriaService;
    }

    @EventListener
    @Transactional
    public void onPedidoCreado(PedidoCreadoEvent event) {
        Usuario usuario = usuarioRepository.findById(event.getUsuarioId()).orElse(null);
        if (usuario == null || Boolean.TRUE.equals(usuario.getEsVip())) {
            return;
        }

        long totalPedidos = pedidoRepository.countByUsuarioId(event.getUsuarioId());

        if (totalPedidos >= pedidosMinimos) {
            usuario.convertirVip();
            usuarioRepository.save(usuario);

            try {
                auditoriaService.registrar(
                        usuario,
                        AccionAuditoria.AUTO,
                        "vip_automatico",
                        usuario.getIdUsuario(),
                        "es_vip=false",
                        "es_vip=true (motivo: " + totalPedidos + " pedidos)"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
