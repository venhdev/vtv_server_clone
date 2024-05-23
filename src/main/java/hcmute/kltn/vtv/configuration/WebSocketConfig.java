package hcmute.kltn.vtv.configuration;

import hcmute.kltn.vtv.util.exception.BadRequestException;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/room");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/room");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(org.springframework.http.server.ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                String token = (String) attributes.get("Authorization");
                if (token != null){
                    throw new BadRequestException("Thiếu thông tin người gửi");
                }
                return () -> token;
            }
        });
    }



}

