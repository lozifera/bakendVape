package com.example.bakend_vape.usuario.security;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(new Email(email))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : "USUARIO";

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(usuario.getEmail().getValue())
                .password(usuario.getPassword().getValue())
                .authorities(rol)
                .build();
    }
}