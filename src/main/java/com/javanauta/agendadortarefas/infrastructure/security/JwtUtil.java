package com.javanauta.agendadortarefas.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    // Chave secreta usada para assinar e verificar tokens JWT
    private final String secretKey = "sua-chave-secreta-super-segura-que-deve-ser-bem-longa";



    // Extrai as claims do token JWT (informações adicionais do token)
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8))) // Define a chave secreta para validar a assinatura do token
                .build()
                .parseClaimsJws(token) // Analisa o token JWT e obtém as claims
                .getBody(); // Retorna o corpo das claims
    }

    // Extrai o email do usuário do token JWT
    public String extrairEmailToken(String token) {
        // Obtém o assunto (nome de usuário) das claims do token
        return extractClaims(token).getSubject();
    }

    // Verifica se o token JWT está expirado
    public boolean isTokenExpired(String token) {
        // Compara a data de expiração do token com a data atual
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Valida o token JWT verificando o nome de usuário e se o token não está expirado
    public boolean validateToken(String token, String username) {
        // Extrai o nome de usuário do token
        final String extractedUsername = extrairEmailToken(token);
        // Verifica se o nome de usuário do token corresponde ao fornecido e se o token não está expirado
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
