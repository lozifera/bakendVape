package com.example.bakend_vape.shared.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class ImagenConfig {

    @Value("${app.upload.dir:uploads/imagenes}")
    public String uploadDir;

    public ImagenConfig() {
        try {
            // Crear la carpeta si no existe
            String uploadPath = "uploads/imagenes";
            Files.createDirectories(Paths.get(uploadPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

