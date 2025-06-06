package com.eventspace.gateway_service.filter;

import com.eventspace.gateway_service.config.JWTUtil;
import com.eventspace.gateway_service.config.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JWTUtil jwtUtil;

    public CustomFilter() {
        super(Config.class);
    }

    public static class Config{}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(routeValidator.isSecured.test(request)){
                if(!request.getHeaders().containsKey("Authorization")){
                   return this.error(exchange,"unAuthenticated User", HttpStatus.UNAUTHORIZED);
                }
                final String headerValue = request.getHeaders().getFirst("Authorization");
                if(headerValue == null || !headerValue.startsWith("Bearer ")){
                      return this.error(exchange, "unAuthorized Access", HttpStatus.UNAUTHORIZED);
                }
                final String token = headerValue.replace("Bearer ", "");
                if(!jwtUtil.validateToken(token))
                    return this.error(exchange, "Session Expired", HttpStatus.UNAUTHORIZED);

                ServerHttpRequest serverHttpRequest = exchange.getRequest()
                        .mutate().header("Authorization", headerValue).build();
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> error(ServerWebExchange exchange, String message, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());

        return response.writeWith(Mono.just(buffer));
    }

}
