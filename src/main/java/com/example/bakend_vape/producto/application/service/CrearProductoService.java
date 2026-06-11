package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.Imagen;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.imagen.domain.repository.ImagenRepository;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import com.example.bakend_vape.producto.application.dto.CrearProductoImagenRequest;
import com.example.bakend_vape.producto.application.dto.CrearProductoRequest;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.application.usecase.CrearProductoUseCase;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrearProductoService implements CrearProductoUseCase {

    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    public CrearProductoService(ProductoRepository productoRepository,
                               ImagenRepository imagenRepository,
                               ImagenProductoRepository imagenProductoRepository,
                               CategoriaRepository categoriaRepository,
                               MarcaRepository marcaRepository) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
    }

    @Override
    public ProductoResponse execute(CrearProductoRequest request) {

        // 1. Validar que la categoría existe
        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // 2. Validar que la marca existe
        Marca marca = marcaRepository.findById(request.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // 3. Crear producto en BD
        Producto producto = new Producto(
                null,
                request.getNombre(),
                request.getDescripcion(),
                request.getPrecio(),
                request.getStock(),
                request.getStockMinimo(),
                categoria,
                marca,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Producto productoGuardado = productoRepository.save(producto);

        // 4. Para cada imagen: crear/reutilizar e asociar
        List<ImagenResponse> imagenesResponse = new ArrayList<>();

        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            for (CrearProductoImagenRequest imagenRequest : request.getImagenes()) {

                // 4.a Buscar si la imagen ya existe (por URL)
                Imagen imagen = imagenRepository.findByUrl(imagenRequest.getUrl())
                        .orElseGet(() -> {
                            // 4.b Si NO existe: crear nueva imagen
                            Imagen nuevaImagen = new Imagen(
                                    null,
                                    imagenRequest.getUrl(),
                                    imagenRequest.getNombre(),
                                    true,  // estado = true por defecto
                                    LocalDateTime.now(),
                                    LocalDateTime.now()
                            );
                            return imagenRepository.save(nuevaImagen);
                        });

                // 4.d Crear asociación en imagen_producto
                ImagenProducto imagenProducto = new ImagenProducto(
                        null,
                        imagen,
                        productoGuardado,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                );

                imagenProductoRepository.save(imagenProducto);

                // Agregar a respuesta
                imagenesResponse.add(new ImagenResponse(
                        imagen.getIdImagen(),
                        imagen.getUrl(),
                        imagen.getNombre(),
                        imagen.getEstado()
                ));
            }
        }

        // 5. Retornar ProductoResponse con imágenes incluidas
        return new ProductoResponse(
                productoGuardado.getIdProducto(),
                productoGuardado.getNombre(),
                productoGuardado.getDescripcion(),
                productoGuardado.getPrecio(),
                productoGuardado.getStock(),
                productoGuardado.getStockMinimo(),
                new com.example.bakend_vape.categoria.application.dto.CategoriaResponse(
                        categoria.getId_categoria(),
                        categoria.getNombre(),
                        new ArrayList<>(),
                        categoria.getCreated_at(),
                        categoria.getUpdated_at()
                ),
                new com.example.bakend_vape.marca.application.dto.MarcaResponse(
                        marca.getId_marca(),
                        marca.getNombre(),
                        marca.getCreated_at(),
                        marca.getUpdated_at()
                ),
                imagenesResponse,
                productoGuardado.getCreatedAt(),
                productoGuardado.getUpdatedAt()
        );
    }
}



