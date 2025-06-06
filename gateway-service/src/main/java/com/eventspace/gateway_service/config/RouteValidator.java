package com.eventspace.gateway_service.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/signup","/auth/login"
    );

    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest -> openApiEndpoints.stream().noneMatch(path -> serverHttpRequest.getURI().getPath().contains(path));

}
