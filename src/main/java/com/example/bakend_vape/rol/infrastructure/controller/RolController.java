package com.example.bakend_vape.rol.infrastructure.controller;

import com.example.bakend_vape.rol.application.dto.CrearRolRequest;
import com.example.bakend_vape.rol.application.dto.RolResponse;
import com.example.bakend_vape.rol.application.usecase.CrearRolUseCase;
import com.example.bakend_vape.rol.application.usecase.ListarRolesUseCase;
import com.example.bakend_vape.rol.application.usecase.ObtenerRolUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final CrearRolUseCase registrar;
    private final ListarRolesUseCase listar;
    private final ObtenerRolUseCase obtener;

    public RolController(CrearRolUseCase registrar, ListarRolesUseCase listar, ObtenerRolUseCase obtener) {
        this.registrar = registrar;
        this.listar = listar;
        this.obtener = obtener;
    }

    @PostMapping
    public RolResponse registrar(@RequestBody CrearRolRequest request) {
        return registrar.execute(request);
    }

    @GetMapping
    public List<RolResponse> listar() {
        return listar.execute();
    }

    @GetMapping("/{id}")
    public RolResponse obtenerPorId(@PathVariable Long id) {
        return obtener.execute(id);
    }
}
