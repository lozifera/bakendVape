package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.atributo.domain.model.Atributo;
import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import com.example.bakend_vape.atributo.domain.repository.ProductoAtributoRepository;
import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
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
import com.example.bakend_vape.producto.application.dto.*;
import com.example.bakend_vape.producto.application.usecase.ActualizarProductoUseCase;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActualizarProductoService implements ActualizarProductoUseCase {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ImagenRepository imagenRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final AtributoRepository atributoRepository;
    private final ProductoAtributoRepository productoAtributoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public ActualizarProductoService(ProductoRepository productoRepository,
                                     CategoriaRepository categoriaRepository,
                                     MarcaRepository marcaRepository,
                                     ImagenRepository imagenRepository,
                                     ImagenProductoRepository imagenProductoRepository,
                                     AtributoRepository atributoRepository,
                                     ProductoAtributoRepository productoAtributoRepository,
                                     UsuarioAutenticadoService usuarioAutenticadoService,
                                     AuditoriaService auditoriaService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.imagenRepository = imagenRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.atributoRepository = atributoRepository;
        this.productoAtributoRepository = productoAtributoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public ProductoResponse execute(Long id, ActualizarProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        String valorAnterior = producto.getNombre();

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

        // Imágenes
        List<ImagenResponse> imagenesResponse = new ArrayList<>();
        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            imagenProductoRepository.findByProductoId(id)
                    .forEach(ip -> imagenProductoRepository.deleteById(ip.getIdImagenProducto()));
            for (CrearProductoImagenRequest imgReq : request.getImagenes()) {
                Imagen imagen = imagenRepository.findByUrl(imgReq.getUrl())
                        .orElseGet(() -> {
                            Imagen nueva = new Imagen(null, imgReq.getUrl(), imgReq.getNombre(), true, LocalDateTime.now(), LocalDateTime.now());
                            return imagenRepository.save(nueva);
                        });
                ImagenProducto relacion = new ImagenProducto(null, imagen, guardado, LocalDateTime.now(), LocalDateTime.now());
                imagenProductoRepository.save(relacion);
                imagenesResponse.add(new ImagenResponse(imagen.getIdImagen(), imagen.getUrl(), imagen.getNombre(), imagen.getEstado()));
            }
        } else {
            List<ImagenProducto> rels = imagenProductoRepository.findByProductoId(guardado.getIdProducto());
            imagenesResponse = rels.stream()
                    .map(rel -> new ImagenResponse(rel.getImagen().getIdImagen(), rel.getImagen().getUrl(), rel.getImagen().getNombre(), rel.getImagen().getEstado()))
                    .toList();
        }

        // Atributos
        List<AtributoEnProductoResponse> atributosResponse = new ArrayList<>();
        if (request.getAtributos() != null && !request.getAtributos().isEmpty()) {
            productoAtributoRepository.deleteByProductoId(id);
            for (ProductoAtributoRequest attrReq : request.getAtributos()) {
                Atributo atributo = atributoRepository.findById(attrReq.getIdAtributo())
                        .orElseThrow(() -> new NotFoundException("Atributo no encontrado id: " + attrReq.getIdAtributo()));
                ProductoAtributo pa = new ProductoAtributo(
                        null, guardado, atributo, attrReq.getValor(),
                        LocalDateTime.now(), LocalDateTime.now()
                );
                productoAtributoRepository.save(pa);
                atributosResponse.add(new AtributoEnProductoResponse(
                        atributo.getIdAtributo(), atributo.getNombre(),
                        atributo.getUnidad(), attrReq.getValor()
                ));
            }
        } else {
            List<ProductoAtributo> rels = productoAtributoRepository.findByProductoId(guardado.getIdProducto());
            atributosResponse = rels.stream()
                    .map(pa -> new AtributoEnProductoResponse(
                            pa.getAtributo().getIdAtributo(),
                            pa.getAtributo().getNombre(),
                            pa.getAtributo().getUnidad(),
                            pa.getValor()
                    )).toList();
        }

        // Auditoría
        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(usuario, AccionAuditoria.UPDATE, "producto", guardado.getIdProducto(), valorAnterior, guardado.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ProductoResponse(
                guardado.getIdProducto(), guardado.getNombre(), guardado.getDescripcion(),
                guardado.getPrecio(), guardado.getStock(), guardado.getStockMinimo(),
                new CategoriaResponse(guardado.getCategoria().getId_categoria(), guardado.getCategoria().getNombre(), new ArrayList<>(), guardado.getCategoria().getCreated_at(), guardado.getCategoria().getUpdated_at()),
                new MarcaResponse(guardado.getMarca().getId_marca(), guardado.getMarca().getNombre(), guardado.getMarca().getCreated_at(), guardado.getMarca().getUpdated_at()),
                imagenesResponse,
                atributosResponse,
                guardado.getCreatedAt(), guardado.getUpdatedAt()
        );
    }
}