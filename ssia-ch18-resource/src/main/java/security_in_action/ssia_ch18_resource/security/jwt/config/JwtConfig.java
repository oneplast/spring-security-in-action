package security_in_action.ssia_ch18_resource.security.jwt.config;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.ObjectUtils;
import security_in_action.ssia_ch18_resource.security.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public JwtDecoder jwtDecoder() {
        return addAudValidator(NimbusJwtDecoder.withJwkSetUri(jwtProperties.getJwkSetUri()).build());
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthConverter = new JwtGrantedAuthoritiesConverter();
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = jwtGrantedAuthConverter.convert(jwt);
            List<String> authoritiesClaim = jwt.getClaimAsStringList("authorities");

            authorities.addAll(authoritiesClaim.stream().map(SimpleGrantedAuthority::new).toList());

            return authorities;
        });
        converter.setPrincipalClaimName("preferred_username");

        return converter;
    }

    private NimbusJwtDecoder addAudValidator(NimbusJwtDecoder decoder) {
        String aud = Optional.ofNullable(jwtProperties.getClaim())
                .map(JwtProperties.Claim::getAud)
                .orElse(null);

        OAuth2TokenValidator<Jwt> audValidator = token -> {
            if (ObjectUtils.isEmpty(aud)) {
                return OAuth2TokenValidatorResult.success();
            }

            List<String> auds = token.getAudience();

            if (auds != null && auds.contains(aud)) {
                return OAuth2TokenValidatorResult.success();
            }

            OAuth2Error err = new OAuth2Error("invalid_token", "Required audience is missing: " + aud, null);
            return OAuth2TokenValidatorResult.failure(err);
        };

        DelegatingOAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefault(), audValidator);

        decoder.setJwtValidator(validator);
        return decoder;
    }
}
