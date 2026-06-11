package com.example.bakend_vape.atributo.application.service;

import com.example.bakend_vape.atributo.application.usecase.EliminarAtributoUseCase;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EliminarAtributoService implements EliminarAtributoUseCase {

    private final AtributoRepository atributoRepository;

    public EliminarAtributoService(AtributoRepository atributoRepository) {
        this.atributoRepository = atributoRepository;
    }

    @Override
    public void execute(Long id) {
        atributoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atributo no encontrado con id: " + id));
        atributoRepository.delete(id);
    }
}
