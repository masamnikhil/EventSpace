package com.eventspace.venue_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       final String requestHeader = request.getHeader("Authorization");
       String token = null, username = null;
       List<String> roles = null;
       if(requestHeader != null && requestHeader.startsWith("Bearer ")) {
           token = requestHeader.substring(7);
           try{
               jwtUtil.validateToken(token);
               username = jwtUtil.extractUsername(token);
               roles = jwtUtil.getRoles(token);
           }catch (JwtException ex){
               response.setStatus(HttpStatus.UNAUTHORIZED.value());
               response.setContentType("application/json");
               Map<String, Object> responseBody= new HashMap<>();
               responseBody.put("status", HttpStatus.UNAUTHORIZED);
               responseBody.put("message", ex.getMessage());
               ObjectMapper mapper = new ObjectMapper();
               String json = mapper.writeValueAsString(responseBody);
               response.getWriter().write(json);
               return;
           }
       }
       if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

           List<GrantedAuthority> authorities = roles.stream()
                   .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                   .collect(Collectors.toList());
           if(jwtUtil.validateToken(token)){
               UsernamePasswordAuthenticationToken authenticationToken =
                       new UsernamePasswordAuthenticationToken(username, null, authorities);

               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

               SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }
       }
        filterChain.doFilter(request, response);
    }
}
