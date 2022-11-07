package rikkei.academy.md4casestudy.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rikkei.academy.md4casestudy.security.userprincipal.UserPrincipal;

import java.time.LocalDate;
import java.util.Date;

@Data
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtConfig jwtConfig;

    public String createToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String username = principal.getUsername();
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .setSubject(username)
                .signWith(jwtConfig.getSecretKeyForSigning())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKeyForSigning()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKeyForSigning())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
