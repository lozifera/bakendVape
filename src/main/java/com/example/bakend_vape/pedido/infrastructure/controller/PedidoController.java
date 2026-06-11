package com.example.bakend_vape.pedido.infrastructure.controller;

import com.example.bakend_vape.pedido.application.dto.CrearPedidoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;
import com.example.bakend_vape.pedido.application.usecase.CrearPedidoUseCase;
import com.example.bakend_vape.pedido.application.usecase.ObtenerPedidoPorIdUseCase;
import com.example.bakend_vape.pedido.application.usecase.ObtenerPedidosUsuarioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CrearPedidoUseCase crearPedidoUseCase;
    private final ObtenerPedidoPorIdUseCase obtenerPedidoPorIdUseCase;
    private final ObtenerPedidosUsuarioUseCase obtenerPedidosUsuarioUseCase;

    public PedidoController(CrearPedidoUseCase crearPedidoUseCase,
                            ObtenerPedidoPorIdUseCase obtenerPedidoPorIdUseCase,
                            ObtenerPedidosUsuarioUseCase obtenerPedidosUsuarioUseCase) {
        this.crearPedidoUseCase = crearPedidoUseCase;
        this.obtenerPedidoPorIdUseCase = obtenerPedidoPorIdUseCase;
        this.obtenerPedidosUsuarioUseCase = obtenerPedidosUsuarioUseCase;
    }

    /** POST /pedidos */
    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@RequestBody CrearPedidoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearPedidoUseCase.execute(request));
    }

    /** GET /pedidos/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(obtenerPedidoPorIdUseCase.execute(id));
    }

    /** GET /pedidos/usuario/{idUsuario} */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PedidoResponse>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(obtenerPedidosUsuarioUseCase.execute(idUsuario));
    }
}
