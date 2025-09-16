package security_in_action.ssia_ch15_auth.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import security_in_action.ssia_ch15_auth.security.oauth2.config.OAuth2PasswordAuthenticationConverter;
import security_in_action.ssia_ch15_auth.security.oauth2.config.OAuth2PasswordAuthenticationProvider;
import security_in_action.ssia_ch15_auth.security.oauth2.jwt.properties.JwtProperties;

@Configuration
@RequiredArgsConstructor
public class AuthServerConfig {

    private final OAuth2AuthorizationServerConfigurer oAuth2AuthServerConfigurer;
    private final OAuth2PasswordAuthenticationConverter oauth2Converter;
    private final RegisteredClientRepository clientRepository;
    private final JwtProperties jwtProperties;

    @Bean
    @Order(1)
    public SecurityFilterChain OAuth2FilterChain(HttpSecurity http, AuthenticationConfiguration authConfig)
            throws Exception {
        AuthenticationManager manager = authConfig.getAuthenticationManager();

        oAuth2AuthServerConfigurer
                .tokenEndpoint(token -> token
                        .accessTokenRequestConverter(oauth2Converter)
                        .authenticationProvider(
                                new OAuth2PasswordAuthenticationProvider(manager, clientRepository, jwtProperties)));

        http
                .securityMatcher(oAuth2AuthServerConfigurer.getEndpointsMatcher())
                .with(oAuth2AuthServerConfigurer, Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
