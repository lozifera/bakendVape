package com.example.bakend_vape.rol.application.service;

import com.example.bakend_vape.rol.application.dto.RolResponse;
import com.example.bakend_vape.rol.application.usecase.ListarRolesUseCase;
import com.example.bakend_vape.rol.domain.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarRolesService implements ListarRolesUseCase {

    private final RolRepository repository;

    public ListarRolesService(RolRepository rolRepository) {
        this.repository = rolRepository;
    }


    @Override
    public List<RolResponse> execute() {
        return repository.findAll().stream().map(rol -> new RolResponse(
                rol.getId_rol(),
                rol.getNombre()
        )).toList();
    }
}
