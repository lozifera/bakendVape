package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VipScheduledService {

    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final AuditoriaService auditoriaService;

    @Value("${vip.antiguedad.meses:12}")
    private int antiguedadMeses;

    @Value("${vip.inactividad.meses:12}")
    private int inactividadMeses;

    public VipScheduledService(UsuarioRepository usuarioRepository,
                               PedidoRepository pedidoRepository,
                               AuditoriaService auditoriaService) {
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
        this.auditoriaService = auditoriaService;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void evaluarVipUsuarios() {
        List<Usuario> todos = usuarioRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        for (Usuario usuario : todos) {
            if (Boolean.TRUE.equals(usuario.getEsVip())) {
                evaluarRevocacion(usuario, ahora);
            } else {
                evaluarConcesion(usuario, ahora);
            }
        }
    }

    private void evaluarConcesion(Usuario usuario, LocalDateTime ahora) {
        if (usuario.getCreated_at() == null) {
            return;
        }

        boolean antiguedadSuficiente = usuario.getCreated_at()
                .plusMonths(antiguedadMeses).isBefore(ahora);

        if (!antiguedadSuficiente) {
            return;
        }

        long totalPedidos = pedidoRepository.countByUsuarioId(usuario.getIdUsuario());

        if (totalPedidos > 0) {
            usuario.convertirVip();
            usuarioRepository.save(usuario);
            registrarAuditoria(usuario, "es_vip=true (motivo: antigüedad " + antiguedadMeses + " meses + compras)");
        }
    }

    private void evaluarRevocacion(Usuario usuario, LocalDateTime ahora) {
        if (usuario.getCreated_at() == null) {
            return;
        }

        var ultimaFecha = pedidoRepository.findUltimaFechaByUsuarioId(usuario.getIdUsuario());

        boolean inactivo = ultimaFecha.isEmpty()
                || ultimaFecha.get().plusMonths(inactividadMeses).isBefore(ahora);

        if (inactivo) {
            usuario.quitarVip();
            usuarioRepository.save(usuario);
            registrarAuditoria(usuario, "es_vip=false (motivo: inactividad > " + inactividadMeses + " meses)");
        }
    }

    private void registrarAuditoria(Usuario usuario, String detalle) {
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.AUTO,
                    "vip_automatico",
                    usuario.getIdUsuario(),
                    null,
                    detalle
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
