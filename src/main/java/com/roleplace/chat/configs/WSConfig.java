package com.roleplace.chat.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.TimeZone;

@Configuration
@EnableWebSocketMessageBroker
public class WSConfig implements WebSocketMessageBrokerConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        // Этот резолвер ищет параметр 'timezone' в запросе или заголовок 'Accept-Language'
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultTimeZone(TimeZone.getTimeZone("UTC+3")); // По умолчанию UTC
        return slr;
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // топики для подписки
        registry.setApplicationDestinationPrefixes("/app"); // куда клиент отправляет
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }
}