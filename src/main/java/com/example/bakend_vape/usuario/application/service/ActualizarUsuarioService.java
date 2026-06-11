package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.usuario.application.dto.ActualizarUsuarioRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;
import com.example.bakend_vape.usuario.application.usecase.ActualizarUsuarioUseCase;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import org.springframework.stereotype.Service;

@Service
public class ActualizarUsuarioService implements ActualizarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public ActualizarUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioResponse execute(Long id, ActualizarUsuarioRequest request) {

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado")) ;

        if (request.getNombre() != null){
            usuario.setNombre(request.getNombre());
        }

        if (request.getApellido() != null){
            usuario.setApellido(request.getApellido());
        }

        if (request.getEmail() != null){
            usuario.setEmail(new Email(request.getEmail()));
        }

        Usuario update = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                update.getIdUsuario(),
                update.getNombre(),
                update.getApellido(),
                update.getEmail().getValue(),
                update.getEsVip(),
                update.getPuntos_actuales(),
                update.getRol().getNombre()
        );
    }
}
