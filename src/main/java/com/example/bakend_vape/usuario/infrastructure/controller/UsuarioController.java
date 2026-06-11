package com.example.bakend_vape.usuario.infrastructure.controller;

import com.example.bakend_vape.usuario.application.dto.ActualizarUsuarioRequest;
import com.example.bakend_vape.usuario.application.dto.CrearUsuarioRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;
import com.example.bakend_vape.usuario.application.usecase.ActualizarUsuarioUseCase;
import com.example.bakend_vape.usuario.application.usecase.CrearUsuarioUseCase;
import com.example.bakend_vape.usuario.application.usecase.ObtenerUsuarioPorIdUseCase;
import com.example.bakend_vape.usuario.application.usecase.ObtenerUsuarioUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ObtenerUsuarioPorIdUseCase obtenerId;
    private final ObtenerUsuarioUseCase obtener;
    private final ActualizarUsuarioUseCase actualizar;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase, ObtenerUsuarioPorIdUseCase obtenerId, ObtenerUsuarioUseCase obtener, ActualizarUsuarioUseCase actualizar) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.obtenerId = obtenerId;
        this.obtener = obtener;
        this.actualizar = actualizar;
    }

    @PostMapping
    public UsuarioResponse registar(@RequestBody CrearUsuarioRequest request){
        return crearUsuarioUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public  UsuarioResponse obtenerPorId(@PathVariable Long id){
        UsuarioResponse response = obtenerId.execute(id);

        System.out.println(response);

        return response;
    }

    @GetMapping
    public List<UsuarioResponse> obtenerTodos(){
        return obtener.execute();
    }

    @PatchMapping("/{id}")
    public UsuarioResponse actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarUsuarioRequest request
    ) {
        return actualizar.execute(id, request);
    }

}
