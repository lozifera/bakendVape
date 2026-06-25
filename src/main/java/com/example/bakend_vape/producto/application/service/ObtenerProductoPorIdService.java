package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;
import com.example.bakend_vape.atributo.domain.repository.ProductoAtributoRepository;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.producto.application.dto.AtributoEnProductoResponse;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import com.example.bakend_vape.producto.application.usecase.ObtenerProductoPorIdUseCase;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerProductoPorIdService implements ObtenerProductoPorIdUseCase {

    private final ProductoRepository productoRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoAtributoRepository productoAtributoRepository;

    public ObtenerProductoPorIdService(ProductoRepository productoRepository,
                                       ImagenProductoRepository imagenProductoRepository,
                                       ProductoAtributoRepository productoAtributoRepository) {
        this.productoRepository = productoRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.productoAtributoRepository = productoAtributoRepository;
    }

    @Override
    public ProductoResponse execute(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        List<ImagenProducto> relacionesImagen = imagenProductoRepository.findByProductoId(id);
        List<ImagenResponse> imagenes = new ArrayList<>();
        for (ImagenProducto rel : relacionesImagen) {
            imagenes.add(new ImagenResponse(
                    rel.getImagen().getIdImagen(),
                    rel.getImagen().getUrl(),
                    rel.getImagen().getNombre(),
                    rel.getImagen().getEstado()
            ));
        }

        List<ProductoAtributo> attrs = productoAtributoRepository.findByProductoId(id);
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
    }
}