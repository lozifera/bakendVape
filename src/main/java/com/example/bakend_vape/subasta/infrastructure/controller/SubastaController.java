package com.example.bakend_vape.subasta.infrastructure.controller;

import com.example.bakend_vape.subasta.application.dto.*;
import com.example.bakend_vape.subasta.application.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints para subastas.
 * Las ofertas en tiempo real se manejan también por WebSocket:
 *   - Conectar:    ws://{host}/ws  (con SockJS)
 *   - Suscribirse: /topic/subastas/{id}
 *   - Ofertar:     /app/subastas/{id}/ofertar
 *   - Resultado:   /topic/subastas/{id}/resultado  (al finalizar)
 */
@RestController
@RequestMapping("/subastas")
public class SubastaController {

    private final CrearSubastaUseCase crearSubastaUseCase;
    private final ObtenerSubastasUseCase obtenerSubastasUseCase;
    private final HacerOfertaUseCase hacerOfertaUseCase;
    private final ObtenerOfertasSubastaUseCase obtenerOfertasSubastaUseCase;
    private final FinalizarSubastaUseCase finalizarSubastaUseCase;

    public SubastaController(CrearSubastaUseCase crearSubastaUseCase,
                             ObtenerSubastasUseCase obtenerSubastasUseCase,
                             HacerOfertaUseCase hacerOfertaUseCase,
                             ObtenerOfertasSubastaUseCase obtenerOfertasSubastaUseCase,
                             FinalizarSubastaUseCase finalizarSubastaUseCase) {
        this.crearSubastaUseCase = crearSubastaUseCase;
        this.obtenerSubastasUseCase = obtenerSubastasUseCase;
        this.hacerOfertaUseCase = hacerOfertaUseCase;
        this.obtenerOfertasSubastaUseCase = obtenerOfertasSubastaUseCase;
        this.finalizarSubastaUseCase = finalizarSubastaUseCase;
    }

    /** POST /subastas  (solo ADMIN) */
    @PostMapping
    public ResponseEntity<SubastaResponse> crear(@RequestBody CrearSubastaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearSubastaUseCase.execute(request));
    }

    /** GET /subastas */
    @GetMapping
    public ResponseEntity<List<SubastaResponse>> listar() {
        return ResponseEntity.ok(obtenerSubastasUseCase.execute());
    }

    /**
     * POST /subastas/{idSubasta}/ofertas
     * También disponible vía WebSocket en /app/subastas/{idSubasta}/ofertar
     */
    @PostMapping("/{idSubasta}/ofertas")
    public ResponseEntity<OfertaSubastaResponse> hacerOferta(@PathVariable Long idSubasta,
                                                             @RequestBody HacerOfertaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hacerOfertaUseCase.execute(idSubasta, request));
    }

    /** GET /subastas/{idSubasta}/ofertas */
    @GetMapping("/{idSubasta}/ofertas")
    public ResponseEntity<List<OfertaSubastaResponse>> listarOfertas(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(obtenerOfertasSubastaUseCase.execute(idSubasta));
    }

    /**
     * POST /subastas/{idSubasta}/finalizar  (solo ADMIN)
     * Cierra la subasta, determina al ganador y lo notifica por WebSocket.
     */
    @PostMapping("/{idSubasta}/finalizar")
    public ResponseEntity<GanadorSubastaResponse> finalizar(@PathVariable Long idSubasta) {
        return ResponseEntity.ok(finalizarSubastaUseCase.execute(idSubasta));
    }
}
