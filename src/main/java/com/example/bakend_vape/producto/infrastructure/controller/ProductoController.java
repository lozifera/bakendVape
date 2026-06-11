package com.example.bakend_vape.producto.infrastructure.controller;

import com.example.bakend_vape.producto.application.dto.ActualizarProductoRequest;
import com.example.bakend_vape.producto.application.dto.CrearProductoRequest;
import com.example.bakend_vape.producto.application.dto.ProductoRankingResponse;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.application.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final CrearProductoUseCase crearProductoUseCase;
    private final ObtenerProductosUseCase obtenerProductosUseCase;
    private final ObtenerProductoPorIdUseCase obtenerProductoPorIdUseCase;
    private final ActualizarProductoUseCase actualizarProductoUseCase;
    private final EliminarProductoUseCase eliminarProductoUseCase;
    private final ObtenerRankingProductosUseCase obtenerRankingProductosUseCase;

    public ProductoController(CrearProductoUseCase crearProductoUseCase,
                              ObtenerProductosUseCase obtenerProductosUseCase,
                              ObtenerProductoPorIdUseCase obtenerProductoPorIdUseCase,
                              ActualizarProductoUseCase actualizarProductoUseCase,
                              EliminarProductoUseCase eliminarProductoUseCase,
                              ObtenerRankingProductosUseCase obtenerRankingProductosUseCase) {
        this.crearProductoUseCase = crearProductoUseCase;
        this.obtenerProductosUseCase = obtenerProductosUseCase;
        this.obtenerProductoPorIdUseCase = obtenerProductoPorIdUseCase;
        this.actualizarProductoUseCase = actualizarProductoUseCase;
        this.eliminarProductoUseCase = eliminarProductoUseCase;
        this.obtenerRankingProductosUseCase = obtenerRankingProductosUseCase;
    }

    /** POST /productos */
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody CrearProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearProductoUseCase.execute(request));
    }

    /** GET /productos */
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(obtenerProductosUseCase.execute());
    }

    /** GET /productos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obtenerProductoPorIdUseCase.execute(id));
    }

    /** PUT /productos/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(@PathVariable Long id,
                                                       @RequestBody ActualizarProductoRequest request) {
        return ResponseEntity.ok(actualizarProductoUseCase.execute(id, request));
    }

    /** DELETE /productos/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarProductoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /productos/ranking?limite=10
     * Devuelve los productos ordenados por volumen de ventas (unidades vendidas).
     */
    @GetMapping("/ranking")
    public ResponseEntity<List<ProductoRankingResponse>> ranking(
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(obtenerRankingProductosUseCase.execute(limite));
    }
}
