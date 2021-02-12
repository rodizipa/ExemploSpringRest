package com.example.Teste.auth.token;

import com.example.Teste.auth.usuario.Usuario;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long EXPIRAEMMS = 86400000;

    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(Authentication authentication){
        Usuario userPrincipal = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(hoje)
                .setExpiration(new Date(hoje.getTime() + EXPIRAEMMS))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Assinatura JWT Inválida. " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Assinatura JWT inválida. " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Assinatura JWT expirou. " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Este token não é suportado. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claim está vazio." + e.getMessage());
        }
        return false;
    }
}
