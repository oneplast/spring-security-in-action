package security_in_action.ssia_ch15_auth.security.oauth2.config;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;

import java.time.Duration;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Configuration
public class OAuth2Config {

/*
    @Bean
    public RegisteredClientRepository clientRepository() {
        RegisteredClient client = RegisteredClient.withId("client")
                .clientId("client")
                .clientSecret("secret")
                .authorizationGrantTypes(set -> set.addAll(
                        List.of(PASSWORD, REFRESH_TOKEN)))
                .scope("read")
                .redirectUri("http://localhost:8080")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(60))
                        .refreshTokenTimeToLive(Duration.ofDays(30))
                        .reuseRefreshTokens(false)
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }
*/

    @Bean
    public RegisteredClientRepository clientRepository() {
        RegisteredClient client = RegisteredClient.withId("client")
                .clientId("client")
                .clientSecret("secret")
                .authorizationGrantTypes(set -> set.addAll(
                        List.of(AUTHORIZATION_CODE, REFRESH_TOKEN)))
                .scope("read")
                .redirectUri("http://localhost:8080/home")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(60))
                        .refreshTokenTimeToLive(Duration.ofDays(30))
                        .reuseRefreshTokens(false)
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    public OAuth2AuthorizationServerConfigurer oAuth2AuthServerConfigurer() {
        return OAuth2AuthorizationServerConfigurer.authorizationServer();
    }
}
