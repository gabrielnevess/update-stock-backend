package com.updatestock.updatestock.jwt;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.updatestock.updatestock.exception.RestError;

public class JwtFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String authToken = null;

    public JwtFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Optional<HttpServletRequest> optReq = Optional.of(request);

        try {

            authToken = optReq.map(req -> req.getHeader("Authorization"))
                              .filter(token -> !token.isEmpty())
                              .map(token -> token.replace("Bearer ", ""))
                              .orElse(null);

            if (authToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService
                        .loadUserByUsername(jwtTokenUtil.getUsernameFromToken(authToken));

                if (userDetails != null && jwtTokenUtil.isTokenValid(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }

            chain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            RestError restError = new RestError(new Date(), "Token JWT expirado");
            responseException(response, restError, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (SignatureException ex) {
            RestError restError = new RestError(new Date(), "Assinatura JWT inválida");
            responseException(response, restError, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (MalformedJwtException ex) {
            RestError restError = new RestError(new Date(), "Token JWT Inválido");
            responseException(response, restError, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            RestError restError = new RestError(new Date(), "Exceção JWT não suportada");
            responseException(response, restError, HttpServletResponse.SC_UNAUTHORIZED);
        } catch (IllegalArgumentException ex) {
            RestError restError = new RestError(new Date(), "JWT afirma que a sequência está vazia");
            responseException(response, restError, HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    private void responseException(HttpServletResponse response, RestError restError, int httpStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(restError));

    }

}