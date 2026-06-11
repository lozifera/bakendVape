package com.example.bakend_vape.rol.application.service;

import com.example.bakend_vape.rol.application.dto.CrearRolRequest;
import com.example.bakend_vape.rol.application.dto.RolResponse;
import com.example.bakend_vape.rol.application.usecase.CrearRolUseCase;
import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.rol.domain.repository.RolRepository;
import org.springframework.stereotype.Service;

@Service
public class CrearRolService implements CrearRolUseCase {

    private final RolRepository repository;

    public CrearRolService(RolRepository repository) {
        this.repository = repository;
    }

    @Override
    public RolResponse execute(CrearRolRequest request) {

        if (repository.existsByNombre(request.getNombre())){
            throw new IllegalArgumentException("El rol con el nombre " + request.getNombre() + " ya existe.");
        }

        Rol rol = new Rol(
                null,
                request.getNombre(),
                null,
                null
        );

        Rol saved = repository.save(rol);

        return new RolResponse(
                saved.getId_rol(),
                saved.getNombre()
        );
    }
}
