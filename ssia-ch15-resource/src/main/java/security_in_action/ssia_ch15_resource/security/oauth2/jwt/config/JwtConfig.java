package security_in_action.ssia_ch15_resource.security.oauth2.jwt.config;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import security_in_action.ssia_ch15_resource.security.oauth2.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(UTF_8));

        return NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthConverter = new JwtGrantedAuthoritiesConverter();
        // 토큰의 클레임에 roles가 있으면 그 이름 사용
        grantedAuthConverter.setAuthoritiesClaimName("roles");
        // prefix로 ROLE_ 사용
        grantedAuthConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(grantedAuthConverter);

        return authConverter;
    }
}
