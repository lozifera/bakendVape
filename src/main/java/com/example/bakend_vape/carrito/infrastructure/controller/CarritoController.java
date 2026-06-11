package com.example.bakend_vape.carrito.infrastructure.controller;

import com.example.bakend_vape.carrito.application.dto.ActualizarCantidadRequest;
import com.example.bakend_vape.carrito.application.dto.AgregarProductoCarritoRequest;
import com.example.bakend_vape.carrito.application.dto.CarritoResponse;
import com.example.bakend_vape.carrito.application.service.FusionarCarritoService;
import com.example.bakend_vape.carrito.application.service.ObtenerCarritoService;
import com.example.bakend_vape.carrito.application.usecase.ActualizarCantidadCarritoUseCase;
import com.example.bakend_vape.carrito.application.usecase.AgregarProductoCarritoUseCase;
import com.example.bakend_vape.carrito.application.usecase.EliminarProductoCarritoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints del carrito de compras.
 *
 * ── Visitante anónimo (sin JWT) ──────────────────────────────────────────
 *  POST   /carrito/productos                  → agregar (request incluye session_id)
 *  GET    /carrito?sessionId={sid}            → consultar carrito anónimo
 *  PUT    /carrito/productos/{id}?sessionId=  → actualizar cantidad
 *  DELETE /carrito/productos/{id}?sessionId=  → eliminar item
 *
 * ── Usuario autenticado (con JWT) ────────────────────────────────────────
 *  POST   /usuarios/{idUsuario}/carrito/productos     → agregar
 *  GET    /usuarios/{idUsuario}/carrito               → consultar
 *  PUT    /usuarios/{idUsuario}/carrito/productos/{id}→ actualizar cantidad
 *  DELETE /usuarios/{idUsuario}/carrito/productos/{id}→ eliminar item
 *  POST   /usuarios/{idUsuario}/carrito/fusionar      → fusionar carrito anónimo
 */
@RestController
public class CarritoController {

    private final AgregarProductoCarritoUseCase agregarProductoUseCase;
    private final ObtenerCarritoService obtenerCarritoService;
    private final ActualizarCantidadCarritoUseCase actualizarCantidadUseCase;
    private final EliminarProductoCarritoUseCase eliminarProductoUseCase;
    private final FusionarCarritoService fusionarCarritoService;

    public CarritoController(AgregarProductoCarritoUseCase agregarProductoUseCase,
                             ObtenerCarritoService obtenerCarritoService,
                             ActualizarCantidadCarritoUseCase actualizarCantidadUseCase,
                             EliminarProductoCarritoUseCase eliminarProductoUseCase,
                             FusionarCarritoService fusionarCarritoService) {
        this.agregarProductoUseCase = agregarProductoUseCase;
        this.obtenerCarritoService = obtenerCarritoService;
        this.actualizarCantidadUseCase = actualizarCantidadUseCase;
        this.eliminarProductoUseCase = eliminarProductoUseCase;
        this.fusionarCarritoService = fusionarCarritoService;
    }

    // ────────────────────────── ANÓNIMOS ──────────────────────────────────

    /** GET /carrito?sessionId=xxx */
    @GetMapping("/carrito")
    public ResponseEntity<CarritoResponse> obtenerCarritoAnonimo(@RequestParam String sessionId) {
        return ResponseEntity.ok(obtenerCarritoService.executeBySession(sessionId));
    }

    /** POST /carrito/productos  (session_id dentro del body) */
    @PostMapping("/carrito/productos")
    public ResponseEntity<CarritoResponse> agregarAnonimo(@RequestBody AgregarProductoCarritoRequest request) {
        return ResponseEntity.ok(agregarProductoUseCase.execute(null, request));
    }

    /** PUT /carrito/productos/{id}?sessionId=xxx */
    @PutMapping("/carrito/productos/{idCarritoProducto}")
    public ResponseEntity<CarritoResponse> actualizarAnonimo(
            @PathVariable Long idCarritoProducto,
            @RequestParam String sessionId,
            @RequestBody ActualizarCantidadRequest request) {
        return ResponseEntity.ok(actualizarCantidadUseCase.execute(null, idCarritoProducto, request, sessionId));
    }

    /** DELETE /carrito/productos/{id}?sessionId=xxx */
    @DeleteMapping("/carrito/productos/{idCarritoProducto}")
    public ResponseEntity<Void> eliminarAnonimo(
            @PathVariable Long idCarritoProducto,
            @RequestParam String sessionId) {
        eliminarProductoUseCase.execute(null, idCarritoProducto, sessionId);
        return ResponseEntity.noContent().build();
    }

    // ────────────────────────── AUTENTICADOS ──────────────────────────────

    /** GET /usuarios/{idUsuario}/carrito */
    @GetMapping("/usuarios/{idUsuario}/carrito")
    public ResponseEntity<CarritoResponse> obtenerCarrito(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(obtenerCarritoService.execute(idUsuario));
    }

    /** POST /usuarios/{idUsuario}/carrito/productos */
    @PostMapping("/usuarios/{idUsuario}/carrito/productos")
    public ResponseEntity<CarritoResponse> agregar(@PathVariable Long idUsuario,
                                                   @RequestBody AgregarProductoCarritoRequest request) {
        return ResponseEntity.ok(agregarProductoUseCase.execute(idUsuario, request));
    }

    /** PUT /usuarios/{idUsuario}/carrito/productos/{id} */
    @PutMapping("/usuarios/{idUsuario}/carrito/productos/{idCarritoProducto}")
    public ResponseEntity<CarritoResponse> actualizar(@PathVariable Long idUsuario,
                                                      @PathVariable Long idCarritoProducto,
                                                      @RequestBody ActualizarCantidadRequest request) {
        return ResponseEntity.ok(actualizarCantidadUseCase.execute(idUsuario, idCarritoProducto, request, null));
    }

    /** DELETE /usuarios/{idUsuario}/carrito/productos/{id} */
    @DeleteMapping("/usuarios/{idUsuario}/carrito/productos/{idCarritoProducto}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idUsuario,
                                         @PathVariable Long idCarritoProducto) {
        eliminarProductoUseCase.execute(idUsuario, idCarritoProducto, null);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /usuarios/{idUsuario}/carrito/fusionar?sessionId=xxx
     * Al hacer login, fusiona el carrito anónimo con el del usuario.
     */
    @PostMapping("/usuarios/{idUsuario}/carrito/fusionar")
    public ResponseEntity<CarritoResponse> fusionar(@PathVariable Long idUsuario,
                                                    @RequestParam String sessionId) {
        return ResponseEntity.ok(fusionarCarritoService.execute(idUsuario, sessionId));
    }
}
