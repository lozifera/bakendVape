package com.example.bakend_vape.categoria.application.service;

import com.example.bakend_vape.categoria.application.dto.CrearCategoriaImagenRequest;
import com.example.bakend_vape.categoria.application.dto.CrearCategoriaRequest;
import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.categoria.application.usecase.CrearCategoriaUseCase;
import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.Imagen;
import com.example.bakend_vape.imagen.domain.model.ImagenCategoria;
import com.example.bakend_vape.imagen.domain.repository.ImagenCategoriaRepository;
import com.example.bakend_vape.imagen.domain.repository.ImagenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrearCategoriaService implements CrearCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final ImagenRepository imagenRepository;
    private final ImagenCategoriaRepository imagenCategoriaRepository;

    public CrearCategoriaService(CategoriaRepository categoriaRepository,
                                ImagenRepository imagenRepository,
                                ImagenCategoriaRepository imagenCategoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.imagenRepository = imagenRepository;
        this.imagenCategoriaRepository = imagenCategoriaRepository;
    }

    @Override
    public CategoriaResponse execute(CrearCategoriaRequest request) {

        // Validar nombre no vacío
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría no puede estar vacío");
        }

        // Validar que la categoría no exista ya
        categoriaRepository.findByNombre(request.getNombre())
                .ifPresent(c -> {
                    throw new RuntimeException("La categoría " + request.getNombre() + " ya existe");
                });

        // Crear categoría
        Categoria categoria = new Categoria(
                null,
                request.getNombre(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        // Procesar imágenes
        List<ImagenResponse> imagenesResponse = new ArrayList<>();

        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            for (CrearCategoriaImagenRequest imagenRequest : request.getImagenes()) {

                // Buscar si la imagen existe (por URL)
                Imagen imagen = imagenRepository.findByUrl(imagenRequest.getUrl())
                        .orElseGet(() -> {
                            // Si no existe: crear nueva imagen
                            Imagen nuevaImagen = new Imagen(
                                    null,
                                    imagenRequest.getUrl(),
                                    imagenRequest.getNombre(),
                                    true,
                                    LocalDateTime.now(),
                                    LocalDateTime.now()
                            );
                            return imagenRepository.save(nuevaImagen);
                        });

                // Crear asociación en imagen_categoria
                ImagenCategoria imagenCategoria = new ImagenCategoria(
                        null,
                        imagen,
                        categoriaGuardada,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );

                imagenCategoriaRepository.save(imagenCategoria);

                // Agregar a respuesta
                imagenesResponse.add(new ImagenResponse(
                        imagen.getIdImagen(),
                        imagen.getUrl(),
                        imagen.getNombre(),
                        imagen.getEstado()
                ));
            }
        }

        return new CategoriaResponse(
                categoriaGuardada.getId_categoria(),
                categoriaGuardada.getNombre(),
                imagenesResponse,
                categoriaGuardada.getCreated_at(),
                categoriaGuardada.getUpdated_at()
        );
    }
}

