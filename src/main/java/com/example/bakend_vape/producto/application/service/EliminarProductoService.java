package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.producto.application.usecase.EliminarProductoUseCase;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EliminarProductoService implements EliminarProductoUseCase {

    private final ProductoRepository productoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;


    public EliminarProductoService(ProductoRepository productoRepository, UsuarioAutenticadoService usuarioAutenticadoService, AuditoriaService auditoriaService) {
        this.productoRepository = productoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void execute(Long id) {

        // 1. OBTENER PRODUCTO
        var producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));

        // 2. GUARDAR VALOR ANTERIOR
        String nombreAnterior = producto.getNombre();

        // 3. ELIMINAR
        productoRepository.delete(id);

        // 4. AUDITORÍA
        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();

        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.DELETE,
                    "producto",
                    id,
                    nombreAnterior,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
