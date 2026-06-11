package com.example.bakend_vape.marca.infrastructure.controller;

import com.example.bakend_vape.marca.application.dto.ActualizarMarcaRequest;
import com.example.bakend_vape.marca.application.dto.CrearMarcaRequest;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import com.example.bakend_vape.marca.application.usecase.ActualizarMarcaUseCase;
import com.example.bakend_vape.marca.application.usecase.CrearMarcaUseCase;
import com.example.bakend_vape.marca.application.usecase.EliminarMarcaUseCase;
import com.example.bakend_vape.marca.application.usecase.ObtenerMarcaPorIdUseCase;
import com.example.bakend_vape.marca.application.usecase.ObtenerMarcasUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final CrearMarcaUseCase crearMarcaUseCase;
    private final ActualizarMarcaUseCase actualizarMarcaUseCase;
    private final ObtenerMarcasUseCase obtenerMarcasUseCase;
    private final ObtenerMarcaPorIdUseCase obtenerMarcaPorIdUseCase;
    private final EliminarMarcaUseCase eliminarMarcaUseCase;

    public MarcaController(CrearMarcaUseCase crearMarcaUseCase,
                         ActualizarMarcaUseCase actualizarMarcaUseCase,
                         ObtenerMarcasUseCase obtenerMarcasUseCase,
                         ObtenerMarcaPorIdUseCase obtenerMarcaPorIdUseCase,
                         EliminarMarcaUseCase eliminarMarcaUseCase) {
        this.crearMarcaUseCase = crearMarcaUseCase;
        this.actualizarMarcaUseCase = actualizarMarcaUseCase;
        this.obtenerMarcasUseCase = obtenerMarcasUseCase;
        this.obtenerMarcaPorIdUseCase = obtenerMarcaPorIdUseCase;
        this.eliminarMarcaUseCase = eliminarMarcaUseCase;
    }

    @PostMapping
    public ResponseEntity<MarcaResponse> crear(@RequestBody CrearMarcaRequest request) {
        MarcaResponse response = crearMarcaUseCase.execute(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> obtenerTodas() {
        List<MarcaResponse> marcas = obtenerMarcasUseCase.execute();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @GetMapping("/{idMarca}")
    public ResponseEntity<MarcaResponse> obtenerPorId(@PathVariable Long idMarca) {
        MarcaResponse marca = obtenerMarcaPorIdUseCase.execute(idMarca);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @PatchMapping("/{idMarca}")
    public ResponseEntity<MarcaResponse> actualizar(@PathVariable Long idMarca,
                                                     @RequestBody ActualizarMarcaRequest request) {
        MarcaResponse response = actualizarMarcaUseCase.execute(idMarca, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{idMarca}")
    public String eliminar(@PathVariable Long idMarca) {
        try {
            eliminarMarcaUseCase.execute(idMarca);
            return "Marca eliminada correctamente";
        } catch (RuntimeException e) {
            return "No se encontró la marca";
        }
    }
}

