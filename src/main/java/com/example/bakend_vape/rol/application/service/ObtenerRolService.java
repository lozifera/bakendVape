package com.example.bakend_vape.rol.application.service;

import com.example.bakend_vape.rol.application.dto.RolResponse;
import com.example.bakend_vape.rol.application.usecase.ObtenerRolUseCase;
import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.rol.domain.repository.RolRepository;
import org.springframework.stereotype.Service;

@Service
public class ObtenerRolService implements ObtenerRolUseCase {

    private final RolRepository repository;

    public ObtenerRolService(RolRepository repository) {
        this.repository = repository;
    }

    @Override
    public RolResponse execute(Long id) {

        Rol rol = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("El rol con id " + id + " no existe."));

        return new RolResponse(
                rol.getId_rol(),
                rol.getNombre()
        );
    }
}
