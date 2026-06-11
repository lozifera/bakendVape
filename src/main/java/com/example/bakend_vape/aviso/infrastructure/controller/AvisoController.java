package com.example.bakend_vape.aviso.infrastructure.controller;

import com.example.bakend_vape.aviso.application.dto.AvisoResponse;
import com.example.bakend_vape.aviso.application.dto.CrearAvisoRequest;
import com.example.bakend_vape.aviso.application.usecase.CrearAvisoUseCase;
import com.example.bakend_vape.aviso.application.usecase.EliminarAvisoUseCase;
import com.example.bakend_vape.aviso.application.usecase.ObtenerAvisosUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avisos")
public class AvisoController {

    private final CrearAvisoUseCase crearAvisoUseCase;
    private final ObtenerAvisosUseCase obtenerAvisosUseCase;
    private final EliminarAvisoUseCase eliminarAvisoUseCase;

    public AvisoController(CrearAvisoUseCase crearAvisoUseCase,
                           ObtenerAvisosUseCase obtenerAvisosUseCase,
                           EliminarAvisoUseCase eliminarAvisoUseCase) {
        this.crearAvisoUseCase = crearAvisoUseCase;
        this.obtenerAvisosUseCase = obtenerAvisosUseCase;
        this.eliminarAvisoUseCase = eliminarAvisoUseCase;
    }

    /** POST /avisos  (solo ADMIN) */
    @PostMapping
    public ResponseEntity<AvisoResponse> crear(@RequestBody CrearAvisoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAvisoUseCase.execute(request));
    }

    /** GET /avisos  (devuelve avisos activos) */
    @GetMapping
    public ResponseEntity<List<AvisoResponse>> listar() {
        return ResponseEntity.ok(obtenerAvisosUseCase.execute());
    }

    /** DELETE /avisos/{id}  (solo ADMIN) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarAvisoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
