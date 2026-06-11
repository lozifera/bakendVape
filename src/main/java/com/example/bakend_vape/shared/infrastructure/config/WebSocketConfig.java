package com.example.bakend_vape.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuración WebSocket con STOMP.
 *
 * Flujo de mensajes:
 *  Cliente suscribe a:  /topic/subastas/{idSubasta}
 *  Cliente envía a:     /app/subastas/{idSubasta}/ofertar
 *  Servidor emite a:    /topic/subastas/{idSubasta}
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // El broker interno gestiona los topics de subastas
        registry.enableSimpleBroker("/topic");
        // Prefijo para mensajes que van al servidor (@MessageMapping)
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint de conexión WebSocket con fallback SockJS para navegadores
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
