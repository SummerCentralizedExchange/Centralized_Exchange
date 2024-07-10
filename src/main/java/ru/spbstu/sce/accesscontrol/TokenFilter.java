package ru.spbstu.sce.accesscontrol;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    // TODO consider use of org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
    private final TokenProvider jwtCore;
    private final UserDetailsService userService;

    public TokenFilter(TokenProvider tokenProvider, UserDetailsService userService) {
        this.jwtCore = tokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        String login;
        UserDetails userDetails;
        UsernamePasswordAuthenticationToken authentication;

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
            }

            if (jwt != null) {
                try {
                    login = jwtCore.getNameFromJwt(jwt);
                } catch (ExpiredJwtException e) {
                    // Обработка истекшего токена
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    userDetails = userService.loadUserByUsername(login);
                    authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Обработка ошибок
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
