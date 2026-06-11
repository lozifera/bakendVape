package com.example.bakend_vape.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Servir imágenes desde la carpeta uploads/imagenes
        String uploadPath = Paths.get("uploads/imagenes").toAbsolutePath().toUri().toString();

        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(3600); // Cache de 1 hora
    }
}

