package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import com.example.bakend_vape.producto.application.dto.ActualizarProductoRequest;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.application.usecase.ActualizarProductoUseCase;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActualizarProductoService implements ActualizarProductoUseCase {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ImagenProductoRepository imagenProductoRepository;

    public ActualizarProductoService(ProductoRepository productoRepository,
                                     CategoriaRepository categoriaRepository,
                                     MarcaRepository marcaRepository,
                                     ImagenProductoRepository imagenProductoRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.imagenProductoRepository = imagenProductoRepository;
    }

    @Override
    public ProductoResponse execute(Long id, ActualizarProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        if (request.getNombre() != null) producto.setNombre(request.getNombre());
        if (request.getDescripcion() != null) producto.setDescripcion(request.getDescripcion());
        if (request.getPrecio() != null) producto.setPrecio(request.getPrecio());
        if (request.getStock() != null) producto.setStock(request.getStock());
        if (request.getStockMinimo() != null) producto.setStockMinimo(request.getStockMinimo());

        if (request.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }
        if (request.getIdMarca() != null) {
            Marca marca = marcaRepository.findById(request.getIdMarca())
                    .orElseThrow(() -> new NotFoundException("Marca no encontrada"));
            producto.setMarca(marca);
        }

        producto.setUpdatedAt(LocalDateTime.now());
        Producto guardado = productoRepository.save(producto);

        List<ImagenProducto> rels = imagenProductoRepository.findByProductoId(guardado.getIdProducto());
        List<ImagenResponse> imagenes = rels.stream()
                .map(rel -> new ImagenResponse(
                        rel.getImagen().getIdImagen(),
                        rel.getImagen().getUrl(),
                        rel.getImagen().getNombre(),
                        rel.getImagen().getEstado()
                )).toList();

        return new ProductoResponse(
                guardado.getIdProducto(),
                guardado.getNombre(),
                guardado.getDescripcion(),
                guardado.getPrecio(),
                guardado.getStock(),
                guardado.getStockMinimo(),
                new com.example.bakend_vape.categoria.application.dto.CategoriaResponse(
                        guardado.getCategoria().getId_categoria(),
                        guardado.getCategoria().getNombre(),
                        new ArrayList<>(),
                        guardado.getCategoria().getCreated_at(),
                        guardado.getCategoria().getUpdated_at()
                ),
                new com.example.bakend_vape.marca.application.dto.MarcaResponse(
                        guardado.getMarca().getId_marca(),
                        guardado.getMarca().getNombre(),
                        guardado.getMarca().getCreated_at(),
                        guardado.getMarca().getUpdated_at()
                ),
                imagenes,
                guardado.getCreatedAt(),
                guardado.getUpdatedAt()
        );
    }
}
