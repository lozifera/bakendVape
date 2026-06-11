package com.example.bakend_vape.puntos.infrastructure.controller;

import com.example.bakend_vape.puntos.application.dto.MovimientoPuntosResponse;
import com.example.bakend_vape.puntos.application.usecase.ObtenerMovimientosPuntosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{idUsuario}/puntos")
public class PuntosController {

    private final ObtenerMovimientosPuntosUseCase obtenerMovimientosUseCase;

    public PuntosController(ObtenerMovimientosPuntosUseCase obtenerMovimientosUseCase) {
        this.obtenerMovimientosUseCase = obtenerMovimientosUseCase;
    }

    /** GET /usuarios/{idUsuario}/puntos */
    @GetMapping
    public ResponseEntity<List<MovimientoPuntosResponse>> listar(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(obtenerMovimientosUseCase.execute(idUsuario));
    }
}
