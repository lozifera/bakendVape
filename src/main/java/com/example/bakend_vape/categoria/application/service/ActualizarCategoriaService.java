package com.example.bakend_vape.categoria.application.service;

import com.example.bakend_vape.categoria.application.dto.ActualizarCategoriaRequest;
import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.categoria.application.dto.CrearCategoriaImagenRequest;
import com.example.bakend_vape.categoria.application.usecase.ActualizarCategoriaUseCase;
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
public class ActualizarCategoriaService implements ActualizarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final ImagenRepository imagenRepository;
    private final ImagenCategoriaRepository imagenCategoriaRepository;

    public ActualizarCategoriaService(CategoriaRepository categoriaRepository,
                                     ImagenRepository imagenRepository,
                                     ImagenCategoriaRepository imagenCategoriaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.imagenRepository = imagenRepository;
        this.imagenCategoriaRepository = imagenCategoriaRepository;
    }

    @Override
    public CategoriaResponse execute(Long idCategoria, ActualizarCategoriaRequest request) {

        // Buscar categoría existente
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Actualizar nombre si se proporciona
        if (request.getNombre() != null && !request.getNombre().trim().isEmpty()) {

            // Validar que el nuevo nombre no esté en uso (excepto por la misma categoría)
            categoriaRepository.findByNombre(request.getNombre())
                    .ifPresent(c -> {
                        if (!c.getId_categoria().equals(idCategoria)) {
                            throw new RuntimeException("El nombre " + request.getNombre() + " ya está en uso");
                        }
                    });

            categoria.setNombre(request.getNombre());
        }

        // Actualizar fecha
        categoria.setUpdated_at(LocalDateTime.now());

        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        // Procesar imágenes si se proporcionan
        List<ImagenResponse> imagenesResponse = new ArrayList<>();

        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            // Eliminar imágenes anteriores
            imagenCategoriaRepository.findByCategoriaId(idCategoria)
                    .forEach(ic -> imagenCategoriaRepository.delete(ic.getIdImagenCategoria()));

            // Agregar nuevas imágenes
            for (CrearCategoriaImagenRequest imagenRequest : request.getImagenes()) {

                Imagen imagen = imagenRepository.findByUrl(imagenRequest.getUrl())
                        .orElseGet(() -> {
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

                ImagenCategoria imagenCategoria = new ImagenCategoria(
                        null,
                        imagen,
                        categoriaActualizada,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );

                imagenCategoriaRepository.save(imagenCategoria);

                imagenesResponse.add(new ImagenResponse(
                        imagen.getIdImagen(),
                        imagen.getUrl(),
                        imagen.getNombre(),
                        imagen.getEstado()
                ));
            }
        } else {
            // Si no hay imágenes en el request, traer las existentes
            List<ImagenCategoria> imagenesExistentes = imagenCategoriaRepository.findByCategoriaId(idCategoria);
            for (ImagenCategoria ic : imagenesExistentes) {
                imagenesResponse.add(new ImagenResponse(
                        ic.getImagen().getIdImagen(),
                        ic.getImagen().getUrl(),
                        ic.getImagen().getNombre(),
                        ic.getImagen().getEstado()
                ));
            }
        }

        return new CategoriaResponse(
                categoriaActualizada.getId_categoria(),
                categoriaActualizada.getNombre(),
                imagenesResponse,
                categoriaActualizada.getCreated_at(),
                categoriaActualizada.getUpdated_at()
        );
    }
}

