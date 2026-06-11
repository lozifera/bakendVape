package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.producto.application.usecase.EliminarProductoUseCase;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EliminarProductoService implements EliminarProductoUseCase {

    private final ProductoRepository productoRepository;

    public EliminarProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void execute(Long id) {
        productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id: " + id));
        productoRepository.delete(id);
    }
}
