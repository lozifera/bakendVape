package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.imagen.domain.model.Imagen;
import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.imagen.domain.repository.ImagenRepository;
import com.example.bakend_vape.producto.application.dto.CrearProductoImagenRequest;
import com.example.bakend_vape.producto.application.usecase.ActualizarImagenesProductoUseCase;
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
public class ActualizarImagenesProductoService implements ActualizarImagenesProductoUseCase {

    private final ProductoRepository productoRepository;
    private final ImagenRepository imagenRepository;
    private final ImagenProductoRepository imagenProductoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public ActualizarImagenesProductoService(ProductoRepository productoRepository,
                                             ImagenRepository imagenRepository,
                                             ImagenProductoRepository imagenProductoRepository,
                                             UsuarioAutenticadoService usuarioAutenticadoService,
                                             AuditoriaService auditoriaService) {
        this.productoRepository = productoRepository;
        this.imagenRepository = imagenRepository;
        this.imagenProductoRepository = imagenProductoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    @Transactional
    public List<ImagenResponse> execute(Long idProducto, List<CrearProductoImagenRequest> imagenes) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + idProducto));

        // Eliminar relaciones anteriores
        List<ImagenProducto> relacionesAnteriores = imagenProductoRepository.findByProductoId(idProducto);
        for (ImagenProducto ip : relacionesAnteriores) {
            imagenProductoRepository.deleteById(ip.getIdImagenProducto());
        }

        // Agregar nuevas imágenes
        List<ImagenResponse> response = new ArrayList<>();
        for (CrearProductoImagenRequest imgReq : imagenes) {
            Imagen imagen = imagenRepository.findByUrl(imgReq.getUrl())
                    .orElseGet(() -> {
                        Imagen nueva = new Imagen(null, imgReq.getUrl(), imgReq.getNombre(), true, LocalDateTime.now(), LocalDateTime.now());
                        return imagenRepository.save(nueva);
                    });

            ImagenProducto relacion = new ImagenProducto(null, imagen, producto, LocalDateTime.now(), LocalDateTime.now());
            imagenProductoRepository.save(relacion);

            response.add(new ImagenResponse(imagen.getIdImagen(), imagen.getUrl(), imagen.getNombre(), imagen.getEstado()));
        }

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.UPDATE,
                    "producto_imagenes",
                    idProducto,
                    null,
                    "Producto: " + producto.getNombre() + " | Imágenes actualizadas: " + imagenes.size()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}