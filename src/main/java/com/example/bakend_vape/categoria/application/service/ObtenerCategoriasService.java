package com.example.bakend_vape.categoria.application.service;

import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.categoria.application.usecase.ObtenerCategoriasUseCase;
import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.repository.ImagenCategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerCategoriasService implements ObtenerCategoriasUseCase {

    private final CategoriaRepository categoriaRepository;
    private final ImagenCategoriaRepository imagenCategoriaRepository;

    public ObtenerCategoriasService(CategoriaRepository categoriaRepository,
                                   ImagenCategoriaRepository imagenCategoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.imagenCategoriaRepository = imagenCategoriaRepository;
    }

    @Override
    public List<CategoriaResponse> execute() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private CategoriaResponse convertirAResponse(Categoria categoria) {
        List<ImagenResponse> imagenes = imagenCategoriaRepository
                .findByCategoriaId(categoria.getId_categoria())
                .stream()
                .map(ic -> new ImagenResponse(
                        ic.getImagen().getIdImagen(),
                        ic.getImagen().getUrl(),
                        ic.getImagen().getNombre(),
                        ic.getImagen().getEstado()
                ))
                .collect(Collectors.toList());

        return new CategoriaResponse(
                categoria.getId_categoria(),
                categoria.getNombre(),
                imagenes,
                categoria.getCreated_at(),
                categoria.getUpdated_at()
        );
    }
}

