package com.example.bakend_vape.direccion.infrastructure.controller;

import com.example.bakend_vape.direccion.application.dto.ActualizarDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.CrearDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.DireccionResponse;
import com.example.bakend_vape.direccion.application.usecase.ActualizarDireccionUseCase;
import com.example.bakend_vape.direccion.application.usecase.CrearDireccionUseCase;
import com.example.bakend_vape.direccion.application.usecase.EliminarDireccionUseCase;
import com.example.bakend_vape.direccion.application.usecase.ObtenerDireccionesUsuarioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    private final CrearDireccionUseCase crear;
    private final ObtenerDireccionesUsuarioUseCase obDirecionU;
    private final ActualizarDireccionUseCase actualizar;
    private final EliminarDireccionUseCase eliminar;

    public DireccionController(CrearDireccionUseCase crear, ObtenerDireccionesUsuarioUseCase obDirecionU, ActualizarDireccionUseCase actualizar, EliminarDireccionUseCase eliminar) {
        this.crear = crear;
        this.obDirecionU = obDirecionU;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
    }
    @PostMapping
    public ResponseEntity<DireccionResponse> crearDireccion(@RequestBody CrearDireccionRequest request) {
        DireccionResponse response = crear.execute(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<DireccionResponse>> obtenerDireccionesUsuario(@PathVariable Long idUsuario) {
        List<DireccionResponse> direcciones = obDirecionU.execute(idUsuario);
        return new ResponseEntity<>(direcciones, HttpStatus.OK);
    }

    @PatchMapping("/{idDireccion}")
    public ResponseEntity<DireccionResponse> actualizarDireccion(@PathVariable Long idDireccion,
        @RequestBody ActualizarDireccionRequest request) {
        DireccionResponse response = actualizar.execute(idDireccion, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{idDireccion}")
    public String eliminarDireccion(@PathVariable Long idDireccion) {
        try {
            eliminar.execute(idDireccion);
            return "Dirección eliminada correctamente";
        } catch (RuntimeException e) {
            return "No se encontró la dirección";
        }
    }
}
