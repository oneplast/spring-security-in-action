package security_in_action.ssia_ch13.security.config;

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
import security_in_action.ssia_ch13.security.oauth2.config.OAuth2PasswordAuthenticationConverter;
import security_in_action.ssia_ch13.security.oauth2.config.OAuth2PasswordAuthenticationProvider;
import security_in_action.ssia_ch13.security.properties.JwtProperty;

@Configuration
@RequiredArgsConstructor
public class AuthServerConfig {

    private final OAuth2PasswordAuthenticationConverter authPasswordConverter;
    private final RegisteredClientRepository clientRepository;
    private final JwtProperty jwtProperty;

    @Bean
    @Order(1)
    public SecurityFilterChain authServerFilterChain(HttpSecurity http, AuthenticationConfiguration authConfig)
            throws Exception {
        AuthenticationManager manager = authConfig.getAuthenticationManager();
        OAuth2AuthorizationServerConfigurer authConfigurer = oauth2AuthServerConfigurer()
                .tokenEndpoint(token -> token
                        .accessTokenRequestConverter(authPasswordConverter)
                        .authenticationProvider(
                                new OAuth2PasswordAuthenticationProvider(manager, clientRepository, jwtProperty)));

        http
                .securityMatcher(authConfigurer.getEndpointsMatcher())
                .with(authConfigurer, Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public OAuth2AuthorizationServerConfigurer oauth2AuthServerConfigurer() {
        return new OAuth2AuthorizationServerConfigurer();
    }
}
