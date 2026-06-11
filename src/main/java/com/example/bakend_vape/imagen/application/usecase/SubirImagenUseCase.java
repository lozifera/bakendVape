package com.example.bakend_vape.imagen.application.usecase;

import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SubirImagenUseCase {
    ImagenResponse execute(MultipartFile archivo) throws IOException;
}

