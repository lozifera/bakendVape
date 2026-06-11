package com.example.bakend_vape.imagen.application.service;

import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.application.usecase.SubirImagenUseCase;
import com.example.bakend_vape.imagen.domain.model.Imagen;
import com.example.bakend_vape.imagen.domain.repository.ImagenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SubirImagenLocalService implements SubirImagenUseCase {

    private final GuardarImagenService guardarImagenService;
    private final ImagenRepository imagenRepository;

    public SubirImagenLocalService(GuardarImagenService guardarImagenService,
                                   ImagenRepository imagenRepository) {
        this.guardarImagenService = guardarImagenService;
        this.imagenRepository = imagenRepository;
    }

    @Override
    public ImagenResponse execute(MultipartFile archivo) throws IOException {

        // 1. Guardar archivo en servidor local
        String urlImagen = guardarImagenService.guardarImagen(archivo);

        // 2. Extraer nombre del archivo original
        String nombreOriginal = archivo.getOriginalFilename();
        if (nombreOriginal == null) {
            nombreOriginal = "imagen";
        }

        // 3. Crear modelo Imagen
        Imagen imagen = new Imagen(
                null,
                urlImagen,  // La URL es la ruta local
                nombreOriginal,
                true,  // estado = true por defecto
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        // 4. Guardar en BD
        Imagen imagenGuardada = imagenRepository.save(imagen);

        // 5. Retornar respuesta
        return new ImagenResponse(
                imagenGuardada.getIdImagen(),
                imagenGuardada.getUrl(),
                imagenGuardada.getNombre(),
                imagenGuardada.getEstado()
        );
    }
}

