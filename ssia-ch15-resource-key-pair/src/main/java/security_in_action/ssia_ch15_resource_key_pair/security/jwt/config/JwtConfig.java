package security_in_action.ssia_ch15_resource_key_pair.security.jwt.config;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import security_in_action.ssia_ch15_resource_key_pair.security.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        String pem = jwtProperties.getPublicKey();

        RSAPublicKey publicKey = (RSAPublicKey) parsePublicKeyFromPem(pem);
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthConverter = new JwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = jwtGrantedAuthConverter.convert(jwt);

            jwt.getClaimAsStringList("roles")
                    .forEach(roles -> authorities.add(new SimpleGrantedAuthority("ROLES_" + roles)));

            return authorities;
        });

        return authConverter;
    }

    private PublicKey parsePublicKeyFromPem(String pem) throws Exception {
        String parsedPem = pem
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] bytes = Base64.getDecoder().decode(parsedPem);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}
