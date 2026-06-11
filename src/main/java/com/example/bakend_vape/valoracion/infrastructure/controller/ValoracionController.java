package com.example.bakend_vape.valoracion.infrastructure.controller;

import com.example.bakend_vape.valoracion.application.dto.CrearValoracionRequest;
import com.example.bakend_vape.valoracion.application.dto.ValoracionResponse;
import com.example.bakend_vape.valoracion.application.usecase.CrearValoracionUseCase;
import com.example.bakend_vape.valoracion.application.usecase.ObtenerValoracionesPorProductoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valoraciones")
public class ValoracionController {

    private final CrearValoracionUseCase crearValoracionUseCase;
    private final ObtenerValoracionesPorProductoUseCase obtenerValoracionesPorProductoUseCase;

    public ValoracionController(CrearValoracionUseCase crearValoracionUseCase,
                                ObtenerValoracionesPorProductoUseCase obtenerValoracionesPorProductoUseCase) {
        this.crearValoracionUseCase = crearValoracionUseCase;
        this.obtenerValoracionesPorProductoUseCase = obtenerValoracionesPorProductoUseCase;
    }

    /** POST /valoraciones */
    @PostMapping
    public ResponseEntity<ValoracionResponse> crear(@RequestBody CrearValoracionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearValoracionUseCase.execute(request));
    }

    /** GET /valoraciones/producto/{idProducto} */
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<ValoracionResponse>> porProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(obtenerValoracionesPorProductoUseCase.execute(idProducto));
    }
}
