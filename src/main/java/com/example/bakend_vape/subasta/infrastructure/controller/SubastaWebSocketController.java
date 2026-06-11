package com.example.bakend_vape.subasta.infrastructure.controller;

import com.example.bakend_vape.subasta.application.dto.HacerOfertaRequest;
import com.example.bakend_vape.subasta.application.dto.OfertaSubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.HacerOfertaUseCase;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Maneja ofertas de subasta en tiempo real mediante STOMP over WebSocket.
 *
 * Flujo:
 *  1. Cliente conecta a ws://.../ws  (con SockJS como fallback)
 *  2. Cliente se suscribe a  /topic/subastas/{idSubasta}
 *  3. Cliente envía oferta a /app/subastas/{idSubasta}/ofertar
 *  4. Servidor valida, persiste y emite la nueva oferta a todos los suscritos
 */
@Controller
public class SubastaWebSocketController {

    private final HacerOfertaUseCase hacerOfertaUseCase;

    public SubastaWebSocketController(HacerOfertaUseCase hacerOfertaUseCase) {
        this.hacerOfertaUseCase = hacerOfertaUseCase;
    }

    /**
     * Recibe una oferta y la retransmite a todos los participantes de esa subasta.
     * Si la oferta es inválida, lanza excepción (el cliente recibe un error STOMP).
     */
    @MessageMapping("/subastas/{idSubasta}/ofertar")
    @SendTo("/topic/subastas/{idSubasta}")
    public OfertaSubastaResponse recibirOferta(
            @DestinationVariable Long idSubasta,
            HacerOfertaRequest request) {

        return hacerOfertaUseCase.execute(idSubasta, request);
    }
}
