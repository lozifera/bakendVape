package com.example.bakend_vape.categoria.infrastructure.controller;

import com.example.bakend_vape.categoria.application.dto.ActualizarCategoriaRequest;
import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.categoria.application.dto.CrearCategoriaRequest;
import com.example.bakend_vape.categoria.application.usecase.ActualizarCategoriaUseCase;
import com.example.bakend_vape.categoria.application.usecase.CrearCategoriaUseCase;
import com.example.bakend_vape.categoria.application.usecase.EliminarCategoriaUseCase;
import com.example.bakend_vape.categoria.application.usecase.ObtenerCategoriaPorIdUseCase;
import com.example.bakend_vape.categoria.application.usecase.ObtenerCategoriasUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CrearCategoriaUseCase crearCategoriaUseCase;
    private final ActualizarCategoriaUseCase actualizarCategoriaUseCase;
    private final ObtenerCategoriasUseCase obtenerCategoriasUseCase;
    private final ObtenerCategoriaPorIdUseCase obtenerCategoriaPorIdUseCase;
    private final EliminarCategoriaUseCase eliminarCategoriaUseCase;

    public CategoriaController(CrearCategoriaUseCase crearCategoriaUseCase,
                             ActualizarCategoriaUseCase actualizarCategoriaUseCase,
                             ObtenerCategoriasUseCase obtenerCategoriasUseCase,
                             ObtenerCategoriaPorIdUseCase obtenerCategoriaPorIdUseCase,
                             EliminarCategoriaUseCase eliminarCategoriaUseCase) {
        this.crearCategoriaUseCase = crearCategoriaUseCase;
        this.actualizarCategoriaUseCase = actualizarCategoriaUseCase;
        this.obtenerCategoriasUseCase = obtenerCategoriasUseCase;
        this.obtenerCategoriaPorIdUseCase = obtenerCategoriaPorIdUseCase;
        this.eliminarCategoriaUseCase = eliminarCategoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@RequestBody CrearCategoriaRequest request) {
        CategoriaResponse response = crearCategoriaUseCase.execute(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> obtenerTodas() {
        List<CategoriaResponse> categorias = obtenerCategoriasUseCase.execute();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponse> obtenerPorId(@PathVariable Long idCategoria) {
        CategoriaResponse categoria = obtenerCategoriaPorIdUseCase.execute(idCategoria);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @PatchMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponse> actualizar(@PathVariable Long idCategoria,
                                                        @RequestBody ActualizarCategoriaRequest request) {
        CategoriaResponse response = actualizarCategoriaUseCase.execute(idCategoria, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{idCategoria}")
    public String eliminar(@PathVariable Long idCategoria) {
        try {
            eliminarCategoriaUseCase.execute(idCategoria);
            return "Categoría eliminada correctamente";
        } catch (RuntimeException e) {
            return "No se encontró la categoría";
        }
    }
}

