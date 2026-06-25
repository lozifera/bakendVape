package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.subasta.application.dto.HacerOfertaRequest;
import com.example.bakend_vape.subasta.application.dto.OfertaSubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.HacerOfertaUseCase;
import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class HacerOfertaService implements HacerOfertaUseCase {

    private final SubastaRepository subastaRepository;
    private final OfertaSubastaRepository ofertaSubastaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public HacerOfertaService(SubastaRepository subastaRepository,
                              OfertaSubastaRepository ofertaSubastaRepository,
                              UsuarioRepository usuarioRepository,
                              SimpMessagingTemplate messagingTemplate,
                              UsuarioAutenticadoService usuarioAutenticadoService,
                              AuditoriaService auditoriaService) {
        this.subastaRepository = subastaRepository;
        this.ofertaSubastaRepository = ofertaSubastaRepository;
        this.usuarioRepository = usuarioRepository;
        this.messagingTemplate = messagingTemplate;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public OfertaSubastaResponse execute(Long idSubasta, HacerOfertaRequest request) {
        Subasta subasta = subastaRepository.findById(idSubasta)
                .orElseThrow(() -> new NotFoundException("Subasta no encontrada"));

        if (subasta.getEstado() != EstadoSubasta.ACTIVA) {
            throw new BusinessException("La subasta no está activa");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // Regla: solo VIP puede ofertar si la subasta es solo_vip
        if (subasta.getSoloVip() && !usuario.getEsVip()) {
            throw new BusinessException("Solo usuarios VIP pueden participar en esta subasta");
        }

        // Regla: la oferta debe superar la oferta vigente
        List<OfertaSubasta> ofertas = ofertaSubastaRepository.findBySubastaId(idSubasta);
        BigDecimal mejorOferta = ofertas.stream()
                .max(Comparator.comparing(o -> o.getMonto().value()))
                .map(o -> o.getMonto().value())
                .orElse(subasta.getPrecioInicial().value());

        if (request.getMonto().compareTo(mejorOferta) <= 0) {
            throw new BusinessException("Tu oferta debe superar la oferta actual de: " + mejorOferta);
        }

        OfertaSubasta nuevaOferta = new OfertaSubasta(
                null, subasta, usuario,
                new Money(request.getMonto()),
                LocalDateTime.now(), LocalDateTime.now()
        );

        OfertaSubasta guardada = ofertaSubastaRepository.save(nuevaOferta);

        OfertaSubastaResponse respuesta = new OfertaSubastaResponse(
                guardada.getIdOfertaSubasta(),
                subasta.getIdSubasta(),
                usuario.getIdUsuario(),
                usuario.getNombre() + " " + usuario.getApellido(),
                guardada.getMonto().value(),
                guardada.getCreatedAt()
        );

        Usuario usuarioAuditoria = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuarioAuditoria,
                    AccionAuditoria.CREATE,
                    "oferta_subasta",
                    guardada.getIdOfertaSubasta(),
                    null,
                    "Subasta: " + subasta.getTitulo() + " | Monto: " + request.getMonto()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Emitir en tiempo real a todos los participantes suscritos a esta subasta
        messagingTemplate.convertAndSend("/topic/subastas/" + idSubasta, respuesta);

        return respuesta;
    }
}
