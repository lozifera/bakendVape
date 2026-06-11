package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.application.usecase.ObtenerProductosUseCase;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerProductosService implements ObtenerProductosUseCase {

    private final ProductoRepository productoRepository;
    private final ImagenProductoRepository imagenProductoRepository;

    public ObtenerProductosService(ProductoRepository productoRepository,
                                   ImagenProductoRepository imagenProductoRepository) {
        this.productoRepository = productoRepository;
        this.imagenProductoRepository = imagenProductoRepository;
    }

    @Override
    public List<ProductoResponse> execute() {
        return productoRepository.findAll().stream().map(producto -> {
            List<ImagenProducto> rels = imagenProductoRepository.findByProductoId(producto.getIdProducto());
            List<ImagenResponse> imagenes = rels.stream()
                    .map(rel -> new ImagenResponse(
                            rel.getImagen().getIdImagen(),
                            rel.getImagen().getUrl(),
                            rel.getImagen().getNombre(),
                            rel.getImagen().getEstado()
                    )).toList();

            return new ProductoResponse(
                    producto.getIdProducto(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getStockMinimo(),
                    new com.example.bakend_vape.categoria.application.dto.CategoriaResponse(
                            producto.getCategoria().getId_categoria(),
                            producto.getCategoria().getNombre(),
                            new ArrayList<>(),
                            producto.getCategoria().getCreated_at(),
                            producto.getCategoria().getUpdated_at()
                    ),
                    new com.example.bakend_vape.marca.application.dto.MarcaResponse(
                            producto.getMarca().getId_marca(),
                            producto.getMarca().getNombre(),
                            producto.getMarca().getCreated_at(),
                            producto.getMarca().getUpdated_at()
                    ),
                    imagenes,
                    producto.getCreatedAt(),
                    producto.getUpdatedAt()
            );
        }).toList();
    }
}
