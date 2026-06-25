package com.example.bakend_vape.categoria.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.categoria.application.usecase.EliminarCategoriaUseCase;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.imagen.domain.repository.ImagenCategoriaRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EliminarCategoriaService implements EliminarCategoriaUseCase {

    private final CategoriaRepository categoriaRepository;
    private final ImagenCategoriaRepository imagenCategoriaRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public EliminarCategoriaService(CategoriaRepository categoriaRepository,
                                   ImagenCategoriaRepository imagenCategoriaRepository,
                                   UsuarioAutenticadoService usuarioAutenticadoService,
                                   AuditoriaService auditoriaService) {
        this.categoriaRepository = categoriaRepository;
        this.imagenCategoriaRepository = imagenCategoriaRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void execute(Long idCategoria) {

        // Validar que la categoría existe
        var categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        String nombreAnterior = categoria.getNombre();

        // Eliminar asociaciones de imágenes
        imagenCategoriaRepository.findByCategoriaId(idCategoria)
                .forEach(ic -> imagenCategoriaRepository.delete(ic.getIdImagenCategoria()));

        // Eliminar la categoría
        categoriaRepository.delete(idCategoria);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.DELETE,
                    "categoria",
                    idCategoria,
                    nombreAnterior,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

