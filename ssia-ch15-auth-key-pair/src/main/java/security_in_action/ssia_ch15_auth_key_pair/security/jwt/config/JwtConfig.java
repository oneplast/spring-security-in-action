package security_in_action.ssia_ch15_auth_key_pair.security.jwt.config;

import static org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS256;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.util.ObjectUtils;
import security_in_action.ssia_ch15_auth_key_pair.security.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        String alias = jwtProperties.getAlias();
        char[] password = jwtProperties.getPassword().toCharArray();

        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream is = new ClassPathResource(jwtProperties.getPrivateKey()).getInputStream()) {
            keyStore.load(is, password);
        }

        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password);
        RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(alias).getPublicKey();

        RSAKey key = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(alias)
                .build();

        return new ImmutableJWKSet<>(new JWKSet(key));
    }

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        return new NimbusJwtEncoder(jwkSource());
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            context.getJwsHeader().algorithm(RS256);
            context.getClaims().issuedAt(Instant.now());

            Set<String> scopes = context.getAuthorizedScopes();
            if (!ObjectUtils.isEmpty(scopes)) {
                context.getClaims().claim("scope", String.join(" ", scopes));
            }

            Authentication principal = context.getPrincipal();
            if (principal != null && principal.getAuthorities() != null) {
                List<String> roles = principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                context.getClaims().claim("roles", roles);
            }

            context.getClaims().claim("generatedInZone", ZoneId.systemDefault().toString());
        };
    }
}
