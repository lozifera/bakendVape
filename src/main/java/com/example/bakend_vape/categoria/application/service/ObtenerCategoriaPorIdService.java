package com.example.bakend_vape.categoria.application.service;

import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.categoria.application.usecase.ObtenerCategoriaPorIdUseCase;
import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.repository.ImagenCategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerCategoriaPorIdService implements ObtenerCategoriaPorIdUseCase {

    private final CategoriaRepository categoriaRepository;
    private final ImagenCategoriaRepository imagenCategoriaRepository;

    public ObtenerCategoriaPorIdService(CategoriaRepository categoriaRepository,
                                       ImagenCategoriaRepository imagenCategoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.imagenCategoriaRepository = imagenCategoriaRepository;
    }

    @Override
    public CategoriaResponse execute(Long idCategoria) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        List<ImagenResponse> imagenes = imagenCategoriaRepository
                .findByCategoriaId(idCategoria)
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

