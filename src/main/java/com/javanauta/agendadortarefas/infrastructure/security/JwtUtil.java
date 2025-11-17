package com.javanauta.agendadortarefas.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {

    private final String secretKey = "c3VhLWNoYXZlLXNlY3JldGEtc3VwZXItc2VndXJhLXF1ZS1kZXZlLXNlci1iZW0tbG9uZ2E=";

    // Gera uma Key a partir da chave secreta String codificada em Base64
    private SecretKey getSecretKey() {
        // Decodifica a chave secreta em Base64 padrão e cria uma SecretKey
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    // Extrai as claims do token JWT (informações adicionais do token)
    private Claims extractClaims(String token) {
        return Jwts.parser() // Inicia o processo de parsing do token JWT
                .verifyWith(getSecretKey()) // Configura o parser para verificar a assinatura do token usando a chave de assinatura fornecida
                .build() // Conclui a configuração do parser
                .parseSignedClaims(token) // Faz o parsing do token e extrai as claims assinadas
                .getPayload(); // Obtém o payload (corpo) do token, que contém as claims
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
