package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.rol.domain.repository.RolRepository;
import com.example.bakend_vape.usuario.application.dto.CrearUsuarioRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;
import com.example.bakend_vape.usuario.application.usecase.CrearUsuarioUseCase;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import com.example.bakend_vape.usuario.domain.valueObject.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CrearUsuarioService implements CrearUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder  passwordEncoder;

    public CrearUsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponse execute(CrearUsuarioRequest request) {

        String passwordEncriptada = passwordEncoder.encode(request.getPassword());

        if (usuarioRepository.existsByEmail(new Email(request.getEmail()))){
            throw new RuntimeException("El email ya está registrado");
        }

        Rol rol = rolRepository.findByNombre("ROLE_CLIENTE").orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario(
                null,
                request.getNombre(),
                request.getApellido(),
                new Email(request.getEmail()),
                new Password(passwordEncriptada),
                false,
                0,
                rol,
                null,
                null
        );

        Usuario saved = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                saved.getIdUsuario(),
                saved.getNombre(),
                saved.getApellido(),
                saved.getEmail().getValue(),
                saved.getEsVip(),
                saved.getPuntos_actuales(),
                saved.getRol().getNombre()
        );
    }
}
