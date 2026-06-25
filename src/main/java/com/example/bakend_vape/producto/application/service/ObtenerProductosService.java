package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;
import com.example.bakend_vape.atributo.domain.repository.ProductoAtributoRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.producto.application.dto.AtributoEnProductoResponse;
import com.example.bakend_vape.producto.application.dto.ProductoFilterParams;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.application.usecase.ObtenerProductosUseCase;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ObtenerProductosService implements ObtenerProductosUseCase {

    private final ProductoRepository productoRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoAtributoRepository productoAtributoRepository;

    public ObtenerProductosService(ProductoRepository productoRepository,
                                   ImagenProductoRepository imagenProductoRepository,
                                   ProductoAtributoRepository productoAtributoRepository) {
        this.productoRepository = productoRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.productoAtributoRepository = productoAtributoRepository;
    }

    @Override
    public List<ProductoResponse> execute() {
        return toResponse(productoRepository.findAll());
    }

    @Override
    public List<ProductoResponse> execute(ProductoFilterParams filtros) {
        List<Producto> productos = productoRepository.findAll();

        if (filtros.categoria() != null) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria().getId_categoria().equals(filtros.categoria()))
                    .toList();
        }

        if (filtros.marca() != null) {
            productos = productos.stream()
                    .filter(p -> p.getMarca().getId_marca().equals(filtros.marca()))
                    .toList();
        }

        if (filtros.search() != null && !filtros.search().isBlank()) {
            String q = filtros.search().toLowerCase();
            productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(q)
                            || (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(q)))
                    .toList();
        }

        if (filtros.atributo() != null && filtros.valorAtributo() != null && !filtros.valorAtributo().isBlank()) {
            Set<Long> idsConAtributo = productoAtributoRepository
                    .findByAtributoIdAndValor(filtros.atributo(), filtros.valorAtributo())
                    .stream()
                    .map(pa -> pa.getProducto().getIdProducto())
                    .collect(Collectors.toSet());
            productos = productos.stream()
                    .filter(p -> idsConAtributo.contains(p.getIdProducto()))
                    .toList();
        }

        return toResponse(productos);
    }

    private List<ProductoResponse> toResponse(List<Producto> productos) {
        return productos.stream().map(producto -> {
            List<ImagenProducto> rels = imagenProductoRepository.findByProductoId(producto.getIdProducto());
            List<ImagenResponse> imagenes = rels.stream()
                    .map(rel -> new ImagenResponse(
                            rel.getImagen().getIdImagen(),
                            rel.getImagen().getUrl(),
                            rel.getImagen().getNombre(),
                            rel.getImagen().getEstado()
                    )).toList();

            List<ProductoAtributo> attrs = productoAtributoRepository.findByProductoId(producto.getIdProducto());
            List<AtributoEnProductoResponse> atributos = attrs.stream()
                    .map(pa -> new AtributoEnProductoResponse(
                            pa.getAtributo().getIdAtributo(),
                            pa.getAtributo().getNombre(),
                            pa.getAtributo().getUnidad(),
                            pa.getValor()
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
                    atributos,
                    producto.getCreatedAt(),
                    producto.getUpdatedAt()
            );
        }).toList();
    }
}