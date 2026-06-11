package com.example.bakend_vape.imagen.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class GuardarImagenService {

    @Value("${app.upload.dir:uploads/imagenes}")
    private String uploadDir;

    /**
     * Guarda un archivo de imagen en la carpeta local
     * @param archivo MultipartFile con la imagen
     * @return URL relativa para acceder a la imagen
     * @throws RuntimeException si hay error en el guardado
     */
    public String guardarImagen(MultipartFile archivo) throws IOException {

        // Validar que el archivo no esté vacío
        if (archivo.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        // Validar tipo de archivo
        String contentType = archivo.getContentType();
        if (contentType == null || !esImagenValida(contentType)) {
            throw new RuntimeException("El archivo no es una imagen válida. Tipos permitidos: PNG, JPG, JPEG, GIF, WEBP");
        }

        // Validar tamaño (máximo 5MB)
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (archivo.getSize() > maxSize) {
            throw new RuntimeException("El archivo excede el tamaño máximo de 5MB");
        }

        // Generar nombre único
        String nombreOriginal = archivo.getOriginalFilename();
        String extension = obtenerExtension(nombreOriginal);
        String nombreUnico = UUID.randomUUID() + "." + extension;

        // Crear la ruta completa
        Path rutaCompleta = Paths.get(uploadDir, nombreUnico);

        // Crear directorio si no existe
        Files.createDirectories(rutaCompleta.getParent());

        // Guardar el archivo
        try {
            Files.write(rutaCompleta, archivo.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }

        // Retornar la URL relativa para acceder a la imagen
        return "/imagenes/" + nombreUnico;
    }

    /**
     * Valida que el tipo de contenido sea una imagen permitida
     */
    private boolean esImagenValida(String contentType) {
        return contentType.equals("image/png") ||
               contentType.equals("image/jpeg") ||
               contentType.equals("image/jpg") ||
               contentType.equals("image/gif") ||
               contentType.equals("image/webp");
    }

    /**
     * Extrae la extensión del archivo
     */
    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return "bin";
        }
        return nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Elimina una imagen del servidor
     */
    public void eliminarImagen(String rutaImagen) {
        try {
            if (rutaImagen != null && rutaImagen.startsWith("/imagenes/")) {
                String nombreArchivo = rutaImagen.replace("/imagenes/", "");
                Path rutaCompleta = Paths.get(uploadDir, nombreArchivo);
                Files.deleteIfExists(rutaCompleta);
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar imagen: " + e.getMessage());
        }
    }
}

