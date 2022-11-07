package rikkei.academy.md4casestudy.security.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private int tokenExpirationAfterDays;

//    @Bean
    public SecretKey getSecretKeyForSigning() {
        return Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }

}
