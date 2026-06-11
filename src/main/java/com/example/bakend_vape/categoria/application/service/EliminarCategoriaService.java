package com.example.bakend_vape.categoria.application.service;

import com.example.bakend_vape.categoria.application.usecase.EliminarCategoriaUseCase;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.domain.repository.ImagenCategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class EliminarCategoriaService implements EliminarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final ImagenCategoriaRepository imagenCategoriaRepository;

    public EliminarCategoriaService(CategoriaRepository categoriaRepository,
                                   ImagenCategoriaRepository imagenCategoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.imagenCategoriaRepository = imagenCategoriaRepository;
    }

    @Override
    public void execute(Long idCategoria) {

        // Validar que la categoría existe
        categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Eliminar asociaciones de imágenes
        imagenCategoriaRepository.findByCategoriaId(idCategoria)
                .forEach(ic -> imagenCategoriaRepository.delete(ic.getIdImagenCategoria()));

        // Eliminar la categoría
        categoriaRepository.delete(idCategoria);
    }
}

