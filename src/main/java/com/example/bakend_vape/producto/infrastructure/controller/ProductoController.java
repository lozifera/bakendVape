package com.example.bakend_vape.producto.infrastructure.controller;

import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.producto.application.dto.*;
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
    private final ActualizarImagenesProductoUseCase actualizarImagenesProductoUseCase;


    public ProductoController(CrearProductoUseCase crearProductoUseCase, ObtenerProductosUseCase obtenerProductosUseCase, ObtenerProductoPorIdUseCase obtenerProductoPorIdUseCase, ActualizarProductoUseCase actualizarProductoUseCase, EliminarProductoUseCase eliminarProductoUseCase, ObtenerRankingProductosUseCase obtenerRankingProductosUseCase, ActualizarImagenesProductoUseCase actualizarImagenesProductoUseCase) {
        this.crearProductoUseCase = crearProductoUseCase;
        this.obtenerProductosUseCase = obtenerProductosUseCase;
        this.obtenerProductoPorIdUseCase = obtenerProductoPorIdUseCase;
        this.actualizarProductoUseCase = actualizarProductoUseCase;
        this.eliminarProductoUseCase = eliminarProductoUseCase;
        this.obtenerRankingProductosUseCase = obtenerRankingProductosUseCase;
        this.actualizarImagenesProductoUseCase = actualizarImagenesProductoUseCase;
    }

    /** POST /productos */
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody CrearProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearProductoUseCase.execute(request));
    }

    /** GET /productos?search=&categoria=&marca=&atributo=&valorAtributo= */
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) Long marca,
            @RequestParam(required = false) Long atributo,
            @RequestParam(required = false) String valorAtributo) {
        if (search == null && categoria == null && marca == null && atributo == null) {
            return ResponseEntity.ok(obtenerProductosUseCase.execute());
        }
        ProductoFilterParams filtros = new ProductoFilterParams(search, categoria, marca, atributo, valorAtributo);
        return ResponseEntity.ok(obtenerProductosUseCase.execute(filtros));
    }

    /** GET /productos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obtenerProductoPorIdUseCase.execute(id));
    }

    /** PUT /productos/{id} */
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarProductoRequest request) {

        return ResponseEntity.ok(
                actualizarProductoUseCase.execute(id, request)
        );
    }

    /** PATCH /productos/{id}/imagenes */
    @PatchMapping("/{id}/imagenes")
    public ResponseEntity<List<ImagenResponse>> actualizarImagenes(
            @PathVariable Long id,
            @RequestBody ActualizarImagenesProductoRequest request) {
        return ResponseEntity.ok(actualizarImagenesProductoUseCase.execute(id, request.getImagenes()));
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
