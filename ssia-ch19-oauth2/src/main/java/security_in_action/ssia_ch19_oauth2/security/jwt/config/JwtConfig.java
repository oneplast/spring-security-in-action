package security_in_action.ssia_ch19_oauth2.security.jwt.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import security_in_action.ssia_ch19_oauth2.security.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return addAudValidator(NimbusReactiveJwtDecoder.withJwkSetUri(jwtProperties.getJwkSetUri()).build());
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthConverter() {
        JwtGrantedAuthoritiesConverter grantedConverter = new JwtGrantedAuthoritiesConverter();
        ReactiveJwtGrantedAuthoritiesConverterAdapter adapter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
                grantedConverter);
        ReactiveJwtAuthenticationConverter reactiveConverter = new ReactiveJwtAuthenticationConverter();

        reactiveConverter.setJwtGrantedAuthoritiesConverter(jwt ->
                Flux.concat(
                        adapter.convert(jwt),
                        Flux.fromIterable(
                                        Optional.ofNullable(jwt.getClaimAsStringList("authorities"))
                                                .orElse(Collections.emptyList()))
                                .filter(auth -> !ObjectUtils.isEmpty(auth))
                                .map(SimpleGrantedAuthority::new)
                )
        );

        reactiveConverter.setPrincipalClaimName("preferred_username");
        return reactiveConverter;
    }

    private NimbusReactiveJwtDecoder addAudValidator(NimbusReactiveJwtDecoder decoder) {
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
