package com.example.bakend_vape.usuario.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ── Públicos ──────────────────────────────────────────
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                        // Consulta de productos, categorías, marcas e imágenes pública
                        .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/marcas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/imagenes/**").permitAll()

                        // Avisos: cualquier usuario autenticado puede leer
                        .requestMatchers(HttpMethod.GET, "/avisos/**").authenticated()

                        // ── Solo ADMIN ────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST,   "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,   "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,   "/marcas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/marcas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/marcas/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,   "/atributos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/atributos/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,   "/subastas").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,   "/avisos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/avisos/**").hasRole("ADMIN")

                        // ── Usuarios autenticados (CLIENTE o ADMIN) ───────────
                        .requestMatchers("/usuarios/**").authenticated()
                        .requestMatchers("/pedidos/**").authenticated()
                        .requestMatchers("/valoraciones/**").authenticated()
                        .requestMatchers("/subastas/**").authenticated()
                        .requestMatchers("/atributos/**").authenticated()

                        // Todo lo demás requiere JWT
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
