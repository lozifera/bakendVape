package com.example.bakend_vape.atributo.infrastructure.controller;

import com.example.bakend_vape.atributo.application.dto.AsignarAtributoProductoRequest;
import com.example.bakend_vape.atributo.application.dto.AtributoResponse;
import com.example.bakend_vape.atributo.application.dto.CrearAtributoRequest;
import com.example.bakend_vape.atributo.application.usecase.AsignarAtributoProductoUseCase;
import com.example.bakend_vape.atributo.application.usecase.CrearAtributoUseCase;
import com.example.bakend_vape.atributo.application.usecase.EliminarAtributoUseCase;
import com.example.bakend_vape.atributo.application.usecase.ObtenerAtributosUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atributos")
public class AtributoController {

    private final CrearAtributoUseCase crearAtributoUseCase;
    private final ObtenerAtributosUseCase obtenerAtributosUseCase;
    private final EliminarAtributoUseCase eliminarAtributoUseCase;
    private final AsignarAtributoProductoUseCase asignarAtributoProductoUseCase;

    public AtributoController(CrearAtributoUseCase crearAtributoUseCase,
                              ObtenerAtributosUseCase obtenerAtributosUseCase,
                              EliminarAtributoUseCase eliminarAtributoUseCase,
                              AsignarAtributoProductoUseCase asignarAtributoProductoUseCase) {
        this.crearAtributoUseCase = crearAtributoUseCase;
        this.obtenerAtributosUseCase = obtenerAtributosUseCase;
        this.eliminarAtributoUseCase = eliminarAtributoUseCase;
        this.asignarAtributoProductoUseCase = asignarAtributoProductoUseCase;
    }

    /** POST /atributos */
    @PostMapping
    public ResponseEntity<AtributoResponse> crear(@RequestBody CrearAtributoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAtributoUseCase.execute(request));
    }

    /** GET /atributos */
    @GetMapping
    public ResponseEntity<List<AtributoResponse>> listar() {
        return ResponseEntity.ok(obtenerAtributosUseCase.execute());
    }

    /** DELETE /atributos/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eliminarAtributoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    /** POST /atributos/producto  — asigna un atributo a un producto */
    @PostMapping("/producto")
    public ResponseEntity<Void> asignarAProducto(@RequestBody AsignarAtributoProductoRequest request) {
        asignarAtributoProductoUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
