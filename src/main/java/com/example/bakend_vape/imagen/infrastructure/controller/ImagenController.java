package com.example.bakend_vape.imagen.infrastructure.controller;

import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.application.usecase.SubirImagenUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final SubirImagenUseCase subirImagenUseCase;

    public ImagenController(SubirImagenUseCase subirImagenUseCase) {
        this.subirImagenUseCase = subirImagenUseCase;
    }

    /**
     * Sube una imagen al servidor local
     * @param archivo Archivo de imagen (PNG, JPG, JPEG, GIF, WEBP)
     * @return ImagenResponse con ID, URL, nombre y estado
     */
    @PostMapping("/upload")
    public ResponseEntity<ImagenResponse> subirImagen(@RequestParam("archivo") MultipartFile archivo) {
        try {
            ImagenResponse response = subirImagenUseCase.execute(archivo);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

