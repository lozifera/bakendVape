package com.example.bakend_vape.producto.application.service;

import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.producto.application.dto.ProductoRankingResponse;
import com.example.bakend_vape.producto.application.usecase.ObtenerRankingProductosUseCase;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerRankingProductosService implements ObtenerRankingProductosUseCase {

    private final PedidoProductoRepository pedidoProductoRepository;

    public ObtenerRankingProductosService(PedidoProductoRepository pedidoProductoRepository) {
        this.pedidoProductoRepository = pedidoProductoRepository;
    }

    @Override
    public List<ProductoRankingResponse> execute(int limite) {
        List<Object[]> raw = pedidoProductoRepository.findRankingProductos(limite > 0 ? limite : 10);
        List<ProductoRankingResponse> ranking = new ArrayList<>();

        for (int i = 0; i < raw.size(); i++) {
            Object[] row = raw.get(i);
            ranking.add(new ProductoRankingResponse(
                    i + 1,                                         // posición
                    ((Number) row[0]).longValue(),                 // idProducto
                    (String) row[1],                               // nombre
                    (BigDecimal) row[2],                           // precio
                    ((Number) row[3]).longValue(),                 // totalVendido
                    (BigDecimal) row[4]                            // ingresosTotales
            ));
        }

        return ranking;
    }
}
