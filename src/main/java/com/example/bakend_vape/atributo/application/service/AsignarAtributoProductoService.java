package com.example.bakend_vape.atributo.application.service;

import com.example.bakend_vape.atributo.application.dto.AsignarAtributoProductoRequest;
import com.example.bakend_vape.atributo.application.usecase.AsignarAtributoProductoUseCase;
import com.example.bakend_vape.atributo.domain.model.Atributo;
import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import com.example.bakend_vape.atributo.domain.repository.ProductoAtributoRepository;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AsignarAtributoProductoService implements AsignarAtributoProductoUseCase {

    private final ProductoAtributoRepository productoAtributoRepository;
    private final ProductoRepository productoRepository;
    private final AtributoRepository atributoRepository;

    public AsignarAtributoProductoService(ProductoAtributoRepository productoAtributoRepository,
                                          ProductoRepository productoRepository,
                                          AtributoRepository atributoRepository) {
        this.productoAtributoRepository = productoAtributoRepository;
        this.productoRepository = productoRepository;
        this.atributoRepository = atributoRepository;
    }

    @Override
    public void execute(AsignarAtributoProductoRequest request) {
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        Atributo atributo = atributoRepository.findById(request.getIdAtributo())
                .orElseThrow(() -> new NotFoundException("Atributo no encontrado"));

        ProductoAtributo pa = new ProductoAtributo(
                null, producto, atributo, request.getValor(),
                LocalDateTime.now(), LocalDateTime.now()
        );
        productoAtributoRepository.save(pa);
    }
}
