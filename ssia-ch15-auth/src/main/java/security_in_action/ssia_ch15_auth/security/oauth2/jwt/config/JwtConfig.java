package security_in_action.ssia_ch15_auth.security.oauth2.jwt.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.jsonwebtoken.security.Keys;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import security_in_action.ssia_ch15_auth.security.oauth2.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(UTF_8));

        return new ImmutableSecret<>(secretKey);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        // JWKSource 를 통해 서명 키를 가져와 토큰 생성
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            context.getJwsHeader().algorithm(MacAlgorithm.HS256);

            Authentication principal = context.getPrincipal();
            if (principal != null && principal.getAuthorities() != null) {
                List<String> roles = principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
                context.getClaims().claim("roles", roles);
            }
        };
    }
}
