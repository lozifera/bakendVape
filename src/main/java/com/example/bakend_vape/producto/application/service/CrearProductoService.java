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
import com.example.bakend_vape.producto.application.usecase.CrearProductoUseCase;
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
public class CrearProductoService implements CrearProductoUseCase {

    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final AtributoRepository atributoRepository;
    private final ProductoAtributoRepository productoAtributoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public CrearProductoService(ProductoRepository productoRepository,
                                ImagenRepository imagenRepository,
                                ImagenProductoRepository imagenProductoRepository,
                                CategoriaRepository categoriaRepository,
                                MarcaRepository marcaRepository,
                                AtributoRepository atributoRepository,
                                ProductoAtributoRepository productoAtributoRepository,
                                UsuarioAutenticadoService usuarioAutenticadoService,
                                AuditoriaService auditoriaService) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.atributoRepository = atributoRepository;
        this.productoAtributoRepository = productoAtributoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public ProductoResponse execute(CrearProductoRequest request) {

        Categoria categoria = categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Marca marca = marcaRepository.findById(request.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

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
        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();

        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.CREATE,
                    "producto",
                    productoGuardado.getIdProducto(),
                    null,
                    productoGuardado.getNombre()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Imágenes
        List<ImagenResponse> imagenesResponse = new ArrayList<>();
        if (request.getImagenes() != null && !request.getImagenes().isEmpty()) {
            for (CrearProductoImagenRequest imagenRequest : request.getImagenes()) {
                Imagen imagen = imagenRepository.findByUrl(imagenRequest.getUrl())
                        .orElseGet(() -> {
                            Imagen nuevaImagen = new Imagen(
                                    null,
                                    imagenRequest.getUrl(),
                                    imagenRequest.getNombre(),
                                    true,
                                    LocalDateTime.now(),
                                    LocalDateTime.now()
                            );
                            return imagenRepository.save(nuevaImagen);
                        });

                ImagenProducto imagenProducto = new ImagenProducto(
                        null, imagen, productoGuardado,
                        LocalDateTime.now(), LocalDateTime.now()
                );
                imagenProductoRepository.save(imagenProducto);

                imagenesResponse.add(new ImagenResponse(
                        imagen.getIdImagen(), imagen.getUrl(),
                        imagen.getNombre(), imagen.getEstado()
                ));
            }
        }

        // Atributos
        List<AtributoEnProductoResponse> atributosResponse = new ArrayList<>();
        if (request.getAtributos() != null && !request.getAtributos().isEmpty()) {
            for (ProductoAtributoRequest attrReq : request.getAtributos()) {
                Atributo atributo = atributoRepository.findById(attrReq.getIdAtributo())
                        .orElseThrow(() -> new NotFoundException("Atributo no encontrado id: " + attrReq.getIdAtributo()));

                ProductoAtributo pa = new ProductoAtributo(
                        null, productoGuardado, atributo, attrReq.getValor(),
                        LocalDateTime.now(), LocalDateTime.now()
                );
                productoAtributoRepository.save(pa);

                atributosResponse.add(new AtributoEnProductoResponse(
                        atributo.getIdAtributo(), atributo.getNombre(),
                        atributo.getUnidad(), attrReq.getValor()
                ));
            }
        }

        return new ProductoResponse(
                productoGuardado.getIdProducto(),
                productoGuardado.getNombre(),
                productoGuardado.getDescripcion(),
                productoGuardado.getPrecio(),
                productoGuardado.getStock(),
                productoGuardado.getStockMinimo(),
                new CategoriaResponse(
                        categoria.getId_categoria(), categoria.getNombre(),
                        new ArrayList<>(), categoria.getCreated_at(), categoria.getUpdated_at()
                ),
                new MarcaResponse(
                        marca.getId_marca(), marca.getNombre(),
                        marca.getCreated_at(), marca.getUpdated_at()
                ),
                imagenesResponse,
                atributosResponse,
                productoGuardado.getCreatedAt(),
                productoGuardado.getUpdatedAt()
        );
    }
}