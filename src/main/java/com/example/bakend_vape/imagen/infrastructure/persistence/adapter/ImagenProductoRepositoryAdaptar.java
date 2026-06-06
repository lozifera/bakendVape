package com.example.bakend_vape.imagen.infrastructure.persistence.adapter;

import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.domain.repository.ImagenProductoRepository;
import com.example.bakend_vape.imagen.infrastructure.mapper.ImagenProductoMapper;
import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenProductoEntity;
import com.example.bakend_vape.imagen.infrastructure.persistence.jpa.JpaImagenProductoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImagenProductoRepositoryAdaptar implements ImagenProductoRepository {

    private final JpaImagenProductoRepository jpa;
    private final ImagenProductoMapper mapper;

    public ImagenProductoRepositoryAdaptar(JpaImagenProductoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new ImagenProductoMapper();
    }

    @Override
    public ImagenProducto save(ImagenProducto imagenProducto) {

        ImagenProductoEntity entity = mapper.toEntity(imagenProducto);
        ImagenProductoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ImagenProducto> findById(Long idImagenProducto) {
        return jpa.findById(idImagenProducto).map(mapper :: toDomain);
    }

    @Override
    public List<ImagenProducto> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void deleteById(Long idImagenProducto) {

        jpa.deleteById(idImagenProducto);

    }

    @Override
    public List<ImagenProducto> findByProductoId(Long idProducto) {
        return jpa.findByProductoIdProducto(idProducto).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<ImagenProducto> findByImagenId(Long idImagen) {
        return jpa.findByImagenIdImagen(idImagen).stream().map(mapper :: toDomain).toList();
    }
}
